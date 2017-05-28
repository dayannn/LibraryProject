package libSource.Attributes;

/**
 * Created by JacKeTUs on 29.05.2017.
 */
public class AttributeProvider extends BaseAttribute {

    public AttributeProvider(String _value)
    {
        tableName = "web_resources";
        name = "resource_provider";
        value = _value;
    }
}
