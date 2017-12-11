import com.caej.admin.BackOfficeModule;
import com.caej.admin.InsuranceEnumModule;
import com.caej.admin.ProductAdminModule;
import com.caej.admin.VendorAdminModule;
import com.caej.admin.customer.CustomerAdminModule;
import com.caej.cart.api.CartModule;
import com.caej.esb.api.ESBModule;
import com.caej.insurance.api.InsuranceModule;
import com.caej.order.OrderModule;
import com.caej.product.api.ProductModule;
import com.caej.underwriting.api.UnderwritingModule;

import app.dealer.api.DealerModule;
import io.sited.Site;
import io.sited.Sited;
import io.sited.file.admin.FileAdminModule;
import io.sited.file.api.FileModule;
import io.sited.file.site.FileWebModule;
import io.sited.message.admin.MessageAdminModule;
import io.sited.user.admin.UserAdminModule;
import io.sited.user.api.UserModule;

/**
 * @author chi
 */
public class Main {
    public static void main(String[] args) {
        Site site = new Site();
        site.install(new UserModule());
        site.install(new UserAdminModule());
        site.install(new FileAdminModule());
        site.install(new FileModule());
        site.install(new FileWebModule());
        site.install(new MessageAdminModule());

        site.install(new ProductModule());
        site.install(new CustomerAdminModule());
        site.install(new InsuranceModule());
        site.install(new InsuranceEnumModule());
        site.install(new CartModule());
        site.install(new OrderModule());
        site.install(new ESBModule());
        site.install(new DealerModule());
        site.install(new BackOfficeModule());
        site.install(new ProductAdminModule());
        site.install(new VendorAdminModule());
        site.install(new UnderwritingModule());

        new Sited()
            .install(site).start();
    }
}
