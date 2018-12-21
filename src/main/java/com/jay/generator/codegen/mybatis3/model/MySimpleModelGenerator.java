package com.jay.generator.codegen.mybatis3.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.codegen.mybatis3.model.SimpleModelGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO 自定义生成model
 * Created by xiang.wei on 2017/12/23
 */
public class MySimpleModelGenerator extends SimpleModelGenerator{
    public MySimpleModelGenerator() {
        super();
    }
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
        this.progressCallback.startTask(Messages.getString("Progress.8", table.toString()));
        Plugin plugins = this.context.getPlugins();
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        FullyQualifiedJavaType superClass = this.getSuperClass();
        if (superClass != null) {
            topLevelClass.setSuperClass(superClass);
            topLevelClass.addImportedType(superClass);
        }

        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getAllColumns();
        if (this.introspectedTable.isConstructorBased()) {
            this.addParameterizedConstructor(topLevelClass);
            if (!this.introspectedTable.isImmutable()) {
                this.addDefaultConstructor(topLevelClass);
            }
        }

        String rootClass = this.getRootClass();
        Iterator i$ = introspectedColumns.iterator();

        while(i$.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)i$.next();
            if (!RootClassInfo.getInstance(rootClass, this.warnings).containsProperty(introspectedColumn)) {
                Field field = this.getJavaBeansField(introspectedColumn);
                if (plugins.modelFieldGenerated(field, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addField(field);
                    topLevelClass.addImportedType(field.getType());
                }

               /* Method method = this.getJavaBeansGetter(introspectedColumn);
                if (plugins.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                    topLevelClass.addMethod(method);
                }

                if (!this.introspectedTable.isImmutable()) {
                    method = this.getJavaBeansSetter(introspectedColumn);
                    if (plugins.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, this.introspectedTable, Plugin.ModelClassType.BASE_RECORD)) {
                        topLevelClass.addMethod(method);
                    }
                }*/
            }
        }

        List<CompilationUnit> answer = new ArrayList();
        if (this.context.getPlugins().modelBaseRecordClassGenerated(topLevelClass, this.introspectedTable)) {
            answer.add(topLevelClass);
        }

        return answer;
    }
    private FullyQualifiedJavaType getSuperClass() {
        String rootClass = this.getRootClass();
        FullyQualifiedJavaType superClass;
        if (rootClass != null) {
            superClass = new FullyQualifiedJavaType(rootClass);
        } else {
            superClass = null;
        }

        return superClass;
    }

    private void addParameterizedConstructor(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        List<IntrospectedColumn> constructorColumns = this.introspectedTable.getAllColumns();
        Iterator i$ = constructorColumns.iterator();

        while(i$.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)i$.next();
            method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), introspectedColumn.getJavaProperty()));
        }

        StringBuilder sb = new StringBuilder();
        IntrospectedColumn introspectedColumn;
        if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
            boolean comma = false;
            sb.append("super(");

            for(i$ = this.introspectedTable.getPrimaryKeyColumns().iterator(); i$.hasNext(); sb.append(introspectedColumn.getJavaProperty())) {
                introspectedColumn = (IntrospectedColumn)i$.next();
                if (comma) {
                    sb.append(", ");
                } else {
                    comma = true;
                }
            }

            sb.append(");");
            method.addBodyLine(sb.toString());
        }

        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getAllColumns();
        i$ = introspectedColumns.iterator();

        while(i$.hasNext()) {
            introspectedColumn = (IntrospectedColumn)i$.next();
            sb.setLength(0);
            sb.append("this.");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" = ");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(';');
            method.addBodyLine(sb.toString());
        }

        topLevelClass.addMethod(method);
    }
}
