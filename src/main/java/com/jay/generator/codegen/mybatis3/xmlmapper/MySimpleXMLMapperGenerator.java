package com.jay.generator.codegen.mybatis3.xmlmapper;

import com.jay.generator.codegen.mybatis3.xmlmapper.elements.MySimpleCountListElementGenerator;
import com.jay.generator.codegen.mybatis3.xmlmapper.elements.MySimpleDeleteByIdElementGenerator;
import com.jay.generator.codegen.mybatis3.xmlmapper.elements.MySimpleInsertElementGenerator;
import com.jay.generator.codegen.mybatis3.xmlmapper.elements.MySimpleQueryPageListElementGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

/**
 * XML生成类
 * Created by  on 2017/12/23
 *
 * @author xiang.wei
 */
public class MySimpleXMLMapperGenerator extends SimpleXMLMapperGenerator {
    public MySimpleXMLMapperGenerator() {
    }

    @Override
    protected XmlElement getSqlMapElement() {
        FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
        this.progressCallback.startTask(Messages.getString("Progress.12", table.toString()));
        XmlElement answer = new XmlElement("mapper");
        String namespace = this.introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", namespace));
        this.context.getCommentGenerator().addRootComment(answer);
        this.addResultMapElement(answer);
        this.addMyDeleteByIdElement(answer);
        this.addMyInsertElement(answer);
        this.addUpdateByPrimaryKeyElement(answer);
        this.addSelectByPrimaryKeyElement(answer);
        this.addSelectAllElement(answer);
       // this.addCountListElement(answer);
        this.addQueryPageListElement(answer);
        return answer;

    }

    /**
     * 插入语句
     *
     * @param parentElement
     */
    protected void addMyInsertElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new MySimpleInsertElementGenerator();
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    /**
     * 语句
     *
     * @param parentElement
     */
    protected void addMyDeleteByIdElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new MySimpleDeleteByIdElementGenerator();
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
    /**
     * 分页语句
     *
     * @param parentElement
     */
    protected void addQueryPageListElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new MySimpleQueryPageListElementGenerator();
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }

    /**
     * 统计数量语句
     *
     * @param parentElement
     */
    protected void addCountListElement(XmlElement parentElement) {
        AbstractXmlElementGenerator elementGenerator = new MySimpleCountListElementGenerator();
        this.initializeAndExecuteGenerator(elementGenerator, parentElement);
    }
}
