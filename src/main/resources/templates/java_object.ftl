<#-- Java object template -->
${className} ${objectName} = new ${className}();
<#-- Loop to generate fields ---------->
<#list fieldList as field>
${objectName}.${field.setMethod}(${field.value});
</#list>
