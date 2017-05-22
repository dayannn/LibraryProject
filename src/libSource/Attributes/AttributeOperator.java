package libSource.Attributes;

/**
 * Created by Антон on 12.05.2017.
 */
public class AttributeOperator extends BaseAttribute
{
    public AttributeOperator(String _value)
    {
        tableName = "operator";
        name = "operator_value";
        value = _value;
        midT = "resource_operator";
    }
}
