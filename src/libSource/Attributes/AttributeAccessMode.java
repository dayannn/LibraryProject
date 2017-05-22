package libSource.Attributes;

/**
 * Created by JacKeTUs on 22.05.2017.
 */

public class AttributeAccessMode extends BaseAttribute
{
    public AttributeAccessMode(String _value)
    {
        tableName = "access_mode";
        name = "access_mode_value";
        value = _value;
    }
}
