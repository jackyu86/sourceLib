import com.caej.api.KdlinsApiModule;
import com.caej.cart.api.CartModule;
import com.caej.esb.api.ESBModule;
import com.caej.insurance.api.InsuranceModule;
import com.caej.order.OrderModule;
import com.caej.product.api.ProductModule;
import com.caej.site.KdlinsWebModule;
import com.caej.underwriting.api.UnderwritingModule;

import app.dealer.api.DealerModule;
import io.sited.Site;
import io.sited.Sited;
import io.sited.customer.api.CustomerModule;
import io.sited.customer.web.CustomerWebModule;
import io.sited.email.api.EmailModule;
import io.sited.file.api.FileModule;
import io.sited.file.site.FileWebModule;
import io.sited.user.api.UserModule;
import io.sited.user.web.UserWebModule;

/**
 * @author chi
 */
public class Main {
    public static void main(String[] args) {
        Site site = new Site();
        site.install(new UserModule());
        site.install(new UserWebModule());
        site.install(new CustomerModule());
        site.install(new CustomerWebModule());
        site.install(new FileModule());
        site.install(new FileWebModule());
        site.install(new EmailModule());

        site.install(new ProductModule());
        site.install(new InsuranceModule());
        site.install(new CartModule());
        site.install(new OrderModule());
        site.install(new UnderwritingModule());
        site.install(new ESBModule());

        site.install(new DealerModule());
        site.install(new KdlinsApiModule());
        site.install(new KdlinsWebModule());

        new Sited()
            .install(site).start();
    }
}
