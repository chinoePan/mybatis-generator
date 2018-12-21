package com.jay.generator.codegen.mybatis3.javamapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author xiang.wei
 * @create 2017/12/25 15:46
 */
public class MyDeleteByPrimaryKeyMethodGenerator extends AbstractJavaMapperMethodGenerator {
   @Override
    public void addInterfaceElements(Interface interfaze) {
       Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
       Method method = new Method();
       method.setVisibility(JavaVisibility.PUBLIC);
       method.setReturnType(FullyQualifiedJavaType.getIntInstance());
       FullyQualifiedJavaType listType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
       method.setName("delete"+listType.getShortName());
       if ( this.introspectedTable.getRules().generatePrimaryKeyClass()) {
           FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
           importedTypes.add(type);
           method.addParameter(new Parameter(type, "key"));
       } else {
           List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
           boolean annotate = introspectedColumns.size() > 1;
           if (annotate) {
               importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
           }

           StringBuilder sb = new StringBuilder();

           Parameter parameter;
           for(Iterator i$ = introspectedColumns.iterator(); i$.hasNext(); method.addParameter(parameter)) {
               IntrospectedColumn introspectedColumn = (IntrospectedColumn)i$.next();
               FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
               importedTypes.add(type);
               parameter = new Parameter(type, introspectedColumn.getJavaProperty());
               if (annotate) {
                   sb.setLength(0);
                   sb.append("@Param(\"");
                   sb.append(introspectedColumn.getJavaProperty());
                   sb.append("\")");
                   parameter.addAnnotation(sb.toString());
               }
           }
       }

       this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
//       this.addMapperAnnotations(interfaze, method);
       if (this.context.getPlugins().clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, this.introspectedTable)) {
           interfaze.addImportedTypes(importedTypes);
           interfaze.addMethod(method);
       }
    }
}
