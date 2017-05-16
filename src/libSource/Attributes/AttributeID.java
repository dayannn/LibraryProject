package libSource.Attributes;

/**
 * Created by Антон on 16.05.2017.
 */
public class AttributeID extends BaseAttribute
{
    public AttributeID(String _value)
    {
        tableName = "web_resources";
        name = "resource_id";
        value = _value;
    }

    public AttributeID(int _value)
    {
        tableName = "web_resources";
        name = "resource_id";
        value = Integer.toString(_value);
    }
}
