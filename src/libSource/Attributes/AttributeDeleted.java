package libSource.Attributes;

public class AttributeDeleted extends BaseAttribute {
    public AttributeDeleted(String _value)
    {
        tableName = "web_resources";
        name = "resource_deleted";
        value = _value;
    }
}
