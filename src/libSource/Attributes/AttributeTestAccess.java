package libSource.Attributes;

/**
 * Created by Антон on 12.05.2017.
 */
public class AttributeTestAccess extends BaseAttribute
{
    public AttributeTestAccess(String _value)
    {
        tableName = "test_mode";
        name = "test_mode_value";
        value = _value;
    }
}
