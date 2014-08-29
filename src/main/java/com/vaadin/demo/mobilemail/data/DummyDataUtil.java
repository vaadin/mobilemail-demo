package com.vaadin.demo.mobilemail.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * A utility class for generating dummy data
 * 
 */
public class DummyDataUtil {

    private static LoremIpsum loremIpsum = new LoremIpsum();
    private static Random randomLorem = new Random();

    /**
     * Creates a new mailbox with the default folders
     * 
     * @param name
     *            The name of the mailbox
     * @return
     */
    private static MailBox createMailbox(String name) {
        MailBox box = new MailBox(name);
        box.setId(idcounter);
        idcounter++;

        return box;
    }

    /**
     * Creates the default folders for a mailbox like Inbox, drafts etc.
     * 
     * @return A list of folders
     */
    private static List<Folder> createDefaultFolders(MailBox box) {
        List<Folder> folders = new ArrayList<Folder>();
        folders.add(createCustomFolder(box, "Inbox"));
        folders.add(createCustomFolder(box, "Drafts"));
        folders.add(createCustomFolder(box, "Sent Mail"));
        folders.add(createCustomFolder(box, "Trash"));
        return folders;
    }

    private static Folder createCustomFolder(AbstractPojo parent, String name) {
        Folder folder = new Folder(name);
        folder.setId(idcounter);
        folder.setParent(parent);

        if (parent instanceof Folder) {
            ((Folder) parent).getChildren().add(folder);
        } else if (parent instanceof MailBox) {
            ((MailBox) parent).getFolders().add(folder);
        }

        idcounter++;
        return folder;
    }

    public static List<Message> createMessages(Folder parent, int amount,
            MessageStatus status) {
        List<Message> messages = new ArrayList<Message>();
        for (int i = 0; i < amount; i++) {
            Message msg = createMessage(parent, status);
            messages.add(msg);
        }
        return messages;
    }

    public static Message createMessage(Folder parent, MessageStatus status) {
        String id = String.valueOf(UUID.randomUUID().getMostSignificantBits())
                .substring(1, 6);

        int paragraphs = 1 + (int) (randomLorem.nextDouble() * 4);

        StringBuilder bodyBuilder = new StringBuilder();
        for (int p = 0; p < paragraphs; p++) {
            int words = 30 + (int) (randomLorem.nextDouble() * 150);
            bodyBuilder.append("<p>");
            bodyBuilder.append(loremIpsum.getWords(words,
                    (int) (randomLorem.nextDouble() * 50)));
            bodyBuilder.append(".</p>");
        }

        String body = bodyBuilder.toString();
        String sender = loremIpsum.getWords(1,
                (int) (randomLorem.nextDouble() * 50));
        sender = sender.replaceAll("\\W", "");
        String reciever = loremIpsum.getWords(1,
                (int) (randomLorem.nextDouble() * 50));
        reciever = reciever.replaceAll("\\W", "");
        String subject = loremIpsum.getWords(
                4 + (int) (randomLorem.nextDouble() * 6),
                (int) (randomLorem.nextDouble() * 50));
        subject = subject.substring(0, 1).toUpperCase() + subject.substring(1);
        String domain = loremIpsum.getWords(1,
                (int) (randomLorem.nextDouble() * 50));
        domain = domain.replaceAll("\\W", "");

        Message msg = new Message(sender + "@" + domain + ".com", reciever
                + "@" + domain + ".com", id + ": " + subject, body);

        // Link both ways
        msg.setParent(parent);
        parent.getChildren().add(msg);

        msg.setId(idcounter);
        idcounter++;
        if (status != null) {
            msg.setStatus(status);
        }
        return msg;
    }

    /**
     * Using a id counter for all pojos so we can use different container
     * instances but still equals and hashcode works.
     */
    private static long idcounter = 0;

    public static MobileMailContainer getContainer() {
        // Zeroing counter to get consistant ids for pojos
        idcounter = 0;

        MobileMailContainer container = new MobileMailContainer();

        MailBox box = createMailbox("Gmail");
        container.addBean(box);
        List<Folder> folders = createDefaultFolders(box);
        container.addAll(folders);

        // Add 3 read messages and 3 new messages to inbox
        Folder inbox = folders.get(0);
        container.addAll(createMessages(inbox, 3, MessageStatus.READ));
        container.addAll(createMessages(inbox, 3, MessageStatus.NEW));

        // Add one message to drafs
        Folder drafts = folders.get(1);
        container.addAll(createMessages(drafts, 1, MessageStatus.UNSENT));

        // Add 10 messages to sent folder
        Folder sent = folders.get(2);
        container.addAll(createMessages(sent, 10, MessageStatus.SENT));

        // Add 150 messages to trash
        Folder trash = folders.get(3);
        container.addAll(createMessages(trash, 150, MessageStatus.READ));

        // Create a mailbox with deep folder structure
        box = createMailbox("Hotmail");
        container.addBean(box);
        folders = createDefaultFolders(box);
        container.addAll(folders);

        // Use 4 levels
        Folder current = createCustomFolder(box, "Level 1");
        container.addBean(current);
        for (int lvl = 2; lvl < 6; lvl++) {
            Folder f = createCustomFolder(current, "Level " + lvl);
            container.addBean(f);
            current = f;
        }

        // add some messages to the last folder
        container.addAll(createMessages(current, 50, MessageStatus.READ));

        // Another mailbox..
        box = createMailbox("Yahoo!");
        container.addBean(box);
        folders = createDefaultFolders(box);
        container.addAll(folders);
        inbox = folders.get(0);
        container.addAll(createMessages(inbox, 300, MessageStatus.NEW));

        // Another mailbox..
        box = createMailbox("iCloud");
        container.addBean(box);
        folders = createDefaultFolders(box);
        container.addAll(folders);

        inbox = folders.get(0);
        container.addAll(createMessages(inbox, 20, MessageStatus.READ));
        container.addAll(createMessages(inbox, 1, MessageStatus.NEW));

        drafts = folders.get(1);
        container.addAll(createMessages(drafts, 0, MessageStatus.UNSENT));

        sent = folders.get(2);
        container.addAll(createMessages(sent, 0, MessageStatus.SENT));

        trash = folders.get(3);
        container.addAll(createMessages(trash, 1, MessageStatus.READ));

        return container;
    }
}
