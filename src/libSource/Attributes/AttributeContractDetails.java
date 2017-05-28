package libSource.Attributes;

/**
 * Created by Антон on 12.05.2017.
 */
public class AttributeContractDetails extends BaseAttribute
{
    public AttributeContractDetails(String _value)
    {
        tableName = "web_resources";
        name = "resource_contract_details";
        value = _value;
    }
}
