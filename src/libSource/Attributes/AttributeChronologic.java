package libSource.Attributes;

/**
 * Created by Антон on 12.05.2017.
 */
public class AttributeChronologic extends BaseAttribute
{
    public AttributeChronologic(String _value)
    {
        tableName = "web_resources";
        name = "resource_chronologic";
        value = _value;
    }
}
