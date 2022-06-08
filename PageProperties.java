package net.nintendo.automation.ui.models.common;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.nintendo.automation.ui.config.ConfigManager;
import net.nintendo.automation.ui.config.yaml.types.UIUrls;
import net.nintendo.automation.ui.models.admin.AdminHomePage;
import net.nintendo.automation.ui.models.d4c.D4CLogin;
import net.nintendo.automation.ui.models.d4c.D4CRomPublishSuit;
import net.nintendo.automation.ui.models.email.EmailDevPage;
import net.nintendo.automation.ui.models.email.EmailPage;
import net.nintendo.automation.ui.models.lcms.LotcheckHomePage;
import net.nintendo.automation.ui.models.lcms.LotcheckLandingPage;
import net.nintendo.automation.ui.models.ndidMS.ManagementSystemLogin;
import net.nintendo.automation.ui.models.ndp.NDPDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.NDPHomePage;
import net.nintendo.automation.ui.models.ndp.NDPMyProductsPage;
import net.nintendo.automation.ui.models.omas.OmasNDIDAuthorization;
import net.nintendo.automation.ui.models.rom.ROMCreateRom;
import net.nintendo.automation.ui.models.rom.ROMLoginPage;
import net.nintendo.automation.ui.models.testrail.TestRailDashboardPage;
import net.nintendo.automation.ui.models.testrail.TestRailLoginPage;
import net.nintendo.automation_core.common_client.enums.ServiceType;
import net.nintendo.automation.ui.models.admin.AdminDevelopmentHome;
import net.nintendo.automation.ui.models.ndp.admin.NDPAdminHomePage;


public class PageProperties {
    private static final Table<Class<? extends CommonUIComponent>, PageState, String> pageUrlStore = HashBasedTable.create();

    static {
        UIUrls ndpUrls = ConfigManager.getEnvironmentConfig().getServiceConfig(ServiceType.NDP).getUi().getUrls();
        UIUrls slcUrls = ConfigManager.getEnvironmentConfig().getServiceConfig(ServiceType.SLCMS).getUi().getUrls();
        UIUrls romUrls = ConfigManager.getEnvironmentConfig().getServiceConfig(ServiceType.SEARCH).getUi().getUrls();
        UIUrls testRailUrls = ConfigManager.getEnvironmentConfig().getServiceConfig(ServiceType.PMMS).getUi().getUrls();

        pageUrlStore.put(LotcheckHomePage.class, PageState.PRE_LOGIN, slcUrls.getRoot());
        pageUrlStore.put(LotcheckLandingPage.class, PageState.POST_LOGIN, slcUrls.getRoot());
        pageUrlStore.put(NDPHomePage.class, PageState.PRE_LOGIN, ndpUrls.getRoot());
        pageUrlStore.put(NDPMyProductsPage.class, PageState.POST_LOGIN, ndpUrls.getRoot() + "/group/development/products");
        pageUrlStore.put(NDPDevelopmentHome.class, PageState.POST_LOGIN, ndpUrls.getRoot() + "/group/development/home");
        pageUrlStore.put(ROMLoginPage.class, PageState.PRE_LOGIN, romUrls.getRoot());
        pageUrlStore.put(ROMCreateRom.class, PageState.POST_LOGIN, romUrls.getRoot() + "/rom");
        pageUrlStore.put(TestRailLoginPage.class, PageState.PRE_LOGIN, testRailUrls.getRoot());
        pageUrlStore.put(TestRailDashboardPage.class, PageState.POST_LOGIN, testRailUrls.getRoot() + "/index.php?/dashboard");
        pageUrlStore.put(OmasNDIDAuthorization.class, PageState.POST_LOGIN, "https://omas2-master.mng.nintendo.net/ndid-auths");
        pageUrlStore.put(D4CRomPublishSuit.class, PageState.PRE_LOGIN, "https://highway.wc.dev5b.d4c.nintendo.net/home");
        pageUrlStore.put(EmailPage.class, PageState.PRE_LOGIN, "http://inbucket.noa.com/mailbox");
        pageUrlStore.put(EmailDevPage.class, PageState.PRE_LOGIN, "https://inbucket.dev1.ms.developer.nintendo.com/");
        pageUrlStore.put(AdminHomePage.class, PageState.PRE_LOGIN, "https://test2-admin.int.developer.nintendo.com/");
        pageUrlStore.put(D4CLogin.class, PageState.PRE_LOGIN, "https://highway.wc.dev5b.d4c.nintendo.net/home");
        pageUrlStore.put(ManagementSystemLogin.class,PageState.PRE_LOGIN,"https://ndidms-master.mng.nintendo.net/ndid/login");
        pageUrlStore.put(AdminDevelopmentHome.class, PageState.POST_LOGIN, "https://test2-admin.int.developer.nintendo.com/group/development/home");
        pageUrlStore.put(NDPAdminHomePage.class,PageState.PRE_LOGIN,"https://test2-admin.int.developer.nintendo.com");
    }

    public static String getUrlForPageState(Class<? extends CommonUIComponent> pageClass, PageState pageState) {
        return pageUrlStore.get(pageClass, pageState);
    }
}