package net.nintendo.automation.email;

import lombok.extern.slf4j.Slf4j;
import net.nintendo.automation.email.models.InbucketEmail;
import net.nintendo.automation.email.models.InbucketEmailInfo;
import net.nintendo.automation.ui.errors.UIException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MailBoxManager {
    private static MailBoxManager instance;
    private static InbucketTestClient client;
    private static InbucketTestClient devClient;

    private MailBoxManager() {
        if(client == null) {
            client = new InbucketTestClient("http://inbucket.noa.com/");
            //devClient = new InbucketTestClient("http://inbucket.dev1.ms.developer.nintendo.com/");
        }
    }

    public static MailBoxManager getInstance() {
        if(instance == null) {
            instance = new MailBoxManager();
        }
        return instance;
    }

    public InbucketEmail getInbucketEmail(String mailboxId, String subject, String date) {
        List<InbucketEmailInfo> messageList = client.getMailbox(mailboxId);
        List<InbucketEmailInfo> matchingEmail = messageList.stream()
                                                        .filter(email -> email.getDate().getText().contentEquals(date) && email.getSubject().getText().contains(subject))
                                                        .collect(Collectors.toList());
        if(matchingEmail.size() == 0) {
            throw new UIException(String.format("No email %s existing in mailbox %s", subject, mailboxId));
        }
        InbucketEmailInfo info = matchingEmail.get(matchingEmail.size() - 1);
        log.info(mailboxId + " " + info.getId());
        return client.getMessage(mailboxId, info.getId());
    }

    public InbucketEmail getInbucketEmail(String mailboxId, String subject) {
        List<InbucketEmailInfo> messageList = client.getMailbox(mailboxId);
        List<InbucketEmailInfo> matchingEmail = messageList.stream()
                                                        .filter(email -> email.getSubject().getText().contains(subject))
                                                        .collect(Collectors.toList());
        if(matchingEmail.size() == 0) {
            throw new UIException(String.format("No email %s existing in mailbox %s", subject, mailboxId));
        }
        InbucketEmailInfo info = matchingEmail.get(matchingEmail.size() - 1);
        log.info(mailboxId + " " + info.getId());
        return client.getMessage(mailboxId, info.getId());
    }

    public InbucketEmail getFirstInbucketEmail(String mailboxId, String subject) {
        List<InbucketEmailInfo> messageList = client.getMailbox(mailboxId);
        List<InbucketEmailInfo> matchingEmail = messageList.stream()
                .filter(email -> email.getSubject().getText().contains(subject))
                .collect(Collectors.toList());
        if(matchingEmail.size() == 0) {
            throw new UIException(String.format("No email %s existing in mailbox %s", subject, mailboxId));
        }
        InbucketEmailInfo info = matchingEmail.get(matchingEmail.size() - 2);
        log.info(mailboxId + " " + info.getId());
        return client.getMessage(mailboxId, info.getId());
    }

    public InbucketEmail getLatestInbucketEmail(String mailboxId) {
        List<InbucketEmailInfo> messageList = client.getMailbox(mailboxId);
        log.info(mailboxId + " " + messageList.get(messageList.size() - 1).getId());
        return client.getMessage(mailboxId, messageList.get(messageList.size() - 1).getId());
    }
}
