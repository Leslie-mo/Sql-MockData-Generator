<#-- Java entity template -->
import lombok.Data;

/**
 * ${classComment}
 */
@Data
public class ${className} implements Serializable {

    <#-- Serialization -->
    private static final long serialVersionUID = 1L;

<#-- Loop to generate fields ---------->
<#list fieldList as field>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    private ${field.javaType} ${field.fieldName};

</#list>
}
