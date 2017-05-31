import com.caej.api.KdlinsApiModule;
import com.caej.cart.CartModuleImpl;
import com.caej.esb.ESBModuleImpl;
import com.caej.insurance.InsuranceModuleImpl;
import com.caej.order.OrderModuleImpl;
import com.caej.product.ProductModuleImpl;
import com.caej.scheduler.JobSchedulerModule;
import com.caej.ticket.TicketModuleImpl;
import com.caej.underwriting.UnderwritingModuleImpl;

import app.dealer.DealerModuleImpl;
import io.sited.Site;
import io.sited.Sited;
import io.sited.customer.CustomerModuleImpl;
import io.sited.email.EmailModuleImpl;
import io.sited.file.FileModuleImpl;
import io.sited.message.MessageModuleImpl;
import io.sited.user.UserModuleImpl;

/**
 * @author chi
 */
public class Main {
    public static void main(String[] args) {
        Site site = new Site();
        site.install(new UserModuleImpl());
        site.install(new CustomerModuleImpl());
        site.install(new FileModuleImpl());
        site.install(new EmailModuleImpl());
        site.install(new MessageModuleImpl());

        site.install(new ProductModuleImpl());
        site.install(new InsuranceModuleImpl());
        site.install(new CartModuleImpl());
        site.install(new OrderModuleImpl());
        site.install(new UnderwritingModuleImpl());

        site.install(new DealerModuleImpl());

        site.install(new TicketModuleImpl());
        site.install(new ESBModuleImpl());
        site.install(new KdlinsApiModule());
        site.install(new JobSchedulerModule());

        new Sited()
            .install(site).start();
    }
}
