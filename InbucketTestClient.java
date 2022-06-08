package net.nintendo.automation.email;

import jakarta.ws.rs.core.UriBuilder;
import net.nintendo.automation.email.models.Content;
import net.nintendo.automation.email.models.InbucketEmail;
import net.nintendo.automation.email.models.InbucketEmailInfo;
import net.nintendo.automation.ui.errors.ConfigException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InbucketTestClient {
    private UriBuilder baseUrl;
    private UriBuilder listMailBoxContents;
    private UriBuilder message;
    private UriBuilder messageHtml;
    private UriBuilder messageSource;

    public InbucketTestClient(String url) {
        this.baseUrl = UriBuilder.fromUri(url);
        listMailBoxContents = baseUrl.clone().path("mailbox/{mailboxName}");
        message = listMailBoxContents.clone().path("/{id}");
        messageHtml = message.clone().path("/html");
        messageSource = message.clone().path("/source");
    }

    public List<InbucketEmailInfo> getMailbox(String mailboxName) {
        List<InbucketEmailInfo> lst = new LinkedList<>();
        try {
            Document doc = Jsoup.connect(listMailBoxContents.build(mailboxName).toString()).parser(Parser.htmlParser()).get();
            doc.getElementsByClass("box listEntry").forEach(ele -> {
                InbucketEmailInfo messageInfo = InbucketEmailInfo.builder()
                        .id(ele.id())
                        .subject(Content.builder()
                                .text(ele.getElementsByClass("subject").text())
                                .html(ele.getElementsByClass("subject").outerHtml())
                                .build())
                        .date(Content.builder()
                                .text(ele.getElementsByClass("date").text())
                                .html(ele.getElementsByClass("date").outerHtml())
                                .build())
                        .from(Content.builder()
                                .text(ele.getElementsByClass("from").text())
                                .html(ele.getElementsByClass("from").outerHtml())
                                .build())
                        .build();
                lst.add(messageInfo);
            });
        } catch (IOException e) {
            throw new ConfigException(e.getMessage());
        }
        return lst;
    }

    public InbucketEmail getMessage(String mailboxName, String id) {
        InbucketEmail msg = new InbucketEmail();
        try {
            Document messageDoc = Jsoup.connect(message.build(mailboxName, id).toString()).parser(Parser.xmlParser()).get();
            Element subject = messageDoc.getElementById("emailSubject");
            Element body = messageDoc.getElementById("emailBody");
            Document bodyDoc = Jsoup.parse(body.text(), "", Parser.xmlParser());
            msg.setSubject(Content.builder()
                    .text(subject.text().trim())
                    .html(subject.html().trim())
                    .build());
            //doc.outputSettings().outline(true);
            List<Element> tables = bodyDoc.body().select("table table table td");
            for(int i = 0; i < tables.size(); i++) {
                Content content = Content.builder().build();
                tables.get(i).getElementsByAttributeValueContaining("align", "right").remove(); //remove date time block
                content.setText(tables.get(i).text().trim());
                content.setHtml(tables.get(i).html().trim());
                if(i == 1) {
                    msg.setBody(content);
                }
                if(i == 2) {
                    msg.setFooter(content);
                }
            }
        } catch (IOException e) {
            throw new ConfigException(e.getMessage());
        }
        return msg;
    }
}
