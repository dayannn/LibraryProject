package libSource.Attributes;

/**
 * Created by Антон on 12.05.2017.
 */
public class AttributeSubscriptionType extends BaseAttribute
{
    public AttributeSubscriptionType(String _value)
    {
        tableName = "subscription_model";
        name = "subscription_model_value";
        value = _value;
    }
}
