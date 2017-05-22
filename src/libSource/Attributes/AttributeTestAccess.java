package libSource.Attributes;

/**
 * Created by Антон on 12.05.2017.
 */
public class AttributeTestAccess extends BaseAttribute
{
    public AttributeTestAccess(String _value)
    {
        tableName = "web_resources";
        name = "resource_test_mode";
        value = _value;
    }
}
