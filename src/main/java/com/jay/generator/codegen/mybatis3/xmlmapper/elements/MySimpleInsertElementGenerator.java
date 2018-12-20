package com.jay.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.GeneratedKey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分页查询
 * @author xiang.wei
 * @create 2017/12/25 9:59
 */
public class MySimpleInsertElementGenerator extends AbstractXmlElementGenerator {
    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("insert");
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        answer.addAttribute(new Attribute("id", this.introspectedTable.getInsertStatementId()+listType.getShortName()));
        FullyQualifiedJavaType parameterType=this.introspectedTable.getRules().calculateAllFieldsClass();

        answer.addAttribute(new Attribute("parameterType", parameterType.getFullyQualifiedName()));
        this.context.getCommentGenerator().addComment(answer);
        GeneratedKey gk = this.introspectedTable.getGeneratedKey();
        if (gk != null) {
            IntrospectedColumn introspectedColumn = this.introspectedTable.getColumn(gk.getColumn());
            if (introspectedColumn != null) {
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute("useGeneratedKeys", "true"));
                    answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                } else {
                    answer.addElement(this.getSelectKey(introspectedColumn, gk));
                }
            }
        }

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();
        insertClause.append("insert into ");
        insertClause.append(this.introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");
        valuesClause.append("values (");
        List<String> valuesClauses = new ArrayList();
        Iterator iter = this.introspectedTable.getAllColumns().iterator();

        while(iter.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
            if (!introspectedColumn.isIdentity()) {
                insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
                valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
                if (iter.hasNext()) {
                    insertClause.append(", ");
                    valuesClause.append(", ");
                }

                if (valuesClause.length() > 80) {
                    answer.addElement(new TextElement(insertClause.toString()));
                    insertClause.setLength(0);
                    OutputUtilities.xmlIndent(insertClause, 1);
                    valuesClauses.add(valuesClause.toString());
                    valuesClause.setLength(0);
                    OutputUtilities.xmlIndent(valuesClause, 1);
                }
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));
        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());
        Iterator i$ = valuesClauses.iterator();

        while(i$.hasNext()) {
            String clause = (String)i$.next();
            answer.addElement(new TextElement(clause));
        }

        if (this.context.getPlugins().sqlMapInsertElementGenerated(answer, this.introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
