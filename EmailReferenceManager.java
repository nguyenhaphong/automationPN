package net.nintendo.automation.email;

import net.nintendo.automation.email.models.EmailReference;
import net.nintendo.automation.email.models.EmailReferenceStore;
import net.nintendo.automation.utils.HtmlUtils;
import net.nintendo.automation_core.common_file_utils.config.YAMLConfigReader;
import net.nintendo.automation_core.common_utils.payloads.MultiLang;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class EmailReferenceManager {
    private static EmailReferenceStore store;
    private static EmailReferenceManager instance;

    private EmailReferenceManager() {
        store = YAMLConfigReader.readYAMLConfigFromResources("emails/email-notification-reference.yml" , EmailReferenceStore.class);
    }

    public static EmailReferenceManager getInstance() {
        if(instance == null) {
            instance = new EmailReferenceManager();
        }
        return instance;
    }

    public EmailReference compose(EmailTemplate template, Map<String, String> data) {
        EmailReference email = store.getEmailReference(template);
        StringSubstitutor placeholder = new StringSubstitutor(data, "${", "}");
        MultiLang subject = new MultiLang();
        subject.setEn(placeholder.replace(email.getSubject().getEn()));
        subject.setJa(placeholder.replace(email.getSubject().getJa()));
        email.setSubject(subject);
        String rawBodyEn = email.getBody().getEn();
        String rawBodyJa = email.getBody().getJa();
        MultiLang body = new MultiLang();
        body.setEn(HtmlUtils.convertHtmlToText(placeholder.replace(rawBodyEn)));
        body.setJa(HtmlUtils.convertHtmlToText(placeholder.replace(rawBodyJa)));
        email.setBody(body);
        MultiLang bodyHtml = new MultiLang();
        bodyHtml.setEn(placeholder.replace(rawBodyEn));
        bodyHtml.setJa(placeholder.replace(rawBodyJa));
        email.setBodyHtml(bodyHtml);
        return email;
    }
}
