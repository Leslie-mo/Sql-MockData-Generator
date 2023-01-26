<#-- Typescript generation template -->
/**
 * ${classComment}
 */
interface ${className} {
<#-- Loop to generate fields ---------->
<#list fieldList as field>
  // ${field.comment}
  ${field.fieldName}: ${field.typescriptType};
</#list>
}
