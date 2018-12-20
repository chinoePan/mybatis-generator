package com.jay.generator.codegen.mybatis3.javamapper.elements;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author xiang.wei
 * @create 2017/12/25 15:46
 */
public class MyInsertMethodGenerator extends AbstractJavaMapperMethodGenerator {
   @Override
    public void addInterfaceElements(Interface interfaze) {
       Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
       Method method = new Method();
       method.setReturnType(FullyQualifiedJavaType.getIntInstance());
       method.setVisibility(JavaVisibility.PUBLIC);
       FullyQualifiedJavaType parameterType;
       parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
       importedTypes.add(parameterType);
       method.setName(this.introspectedTable.getInsertStatementId()+parameterType.getShortName());
       method.addParameter(new Parameter(parameterType, "record"));
       this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
       if (this.context.getPlugins().clientInsertMethodGenerated(method, interfaze, this.introspectedTable)) {
           interfaze.addImportedTypes(importedTypes);
           interfaze.addMethod(method);
       }
    }
}
