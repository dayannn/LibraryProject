package libSource.Attributes;

/**
 * Created by JacKeTUs on 29.05.2017.
 */
public class AttributeStatistics extends BaseAttribute {

    public AttributeStatistics(String _value)
    {
        tableName = "web_resources";
        name = "resource_stat";
        value = _value;
    }
}
