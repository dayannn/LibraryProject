package libSource.Attributes;


public class AttributeArchiveKey extends BaseAttribute
{
    public AttributeArchiveKey(String _value)
    {
        tableName = "web_resources";
        name = "key";
        value = _value;
    }

    public AttributeArchiveKey(int _value)
    {
        tableName = "web_resources";
        name = "key";
        value = Integer.toString(_value);
    }
}
