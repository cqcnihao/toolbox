package com.git.toolbox.util;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.Permission;
import java.security.PermissionCollection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * This program demonstrates how to download e-mail messages and save
 * attachments into files on disk.
 *
 * @author www.codejava.net
 */
public class EmailAttachmentReceiver {
    private String saveDirectory;

    /**
     * Sets the directory where attached files will be stored.
     *
     * @param dir absolute path of the directory
     */
    public void setSaveDirectory(String dir) {
        this.saveDirectory = dir;
    }

    /**
     * Downloads new messages and saves attachments to disk if any.
     *
     * @param host
     * @param port
     * @param userName
     * @param password
     */
    public void downloadEmailAttachments(String host, String port,
                                         String userName, String password) {
        Properties properties = new Properties();

        // server setting
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);

        // SSL setting
        properties.setProperty("mail.imap.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.imap.socketFactory.fallback", "false");
        properties.setProperty("mail.imap.socketFactory.port",
                String.valueOf(port));

        Session session = Session.getDefaultInstance(properties);

        try {
            // connects to the message store
            Store store = session.getStore("imap");
            store.connect(userName, password);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);

            // fetches new messages from server
            Message[] arrayMessages = folderInbox.getMessages();

            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                Address[] fromAddress = message.getFrom();
                String from = fromAddress[0].toString();
                String subject = message.getSubject();
                String sentDate = message.getSentDate().toString();
                Date now = new Date(sentDate);

                Instant end = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
                Instant start = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();


                    String contentType = message.getContentType();
                    String messageContent = "";

                    // store attachment file name, separated by comma
                    String attachFiles = "";

                    if (contentType.contains("multipart")) {
                        // content may contain attachments
                        Multipart multiPart = (Multipart) message.getContent();
                        int numberOfParts = multiPart.getCount();
                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                // this part is attachment
                                String fileName = part.getFileName();
                                fileName = MimeUtility.decodeText(fileName);
                                attachFiles += fileName + ", ";
                                part.saveFile(saveDirectory + File.separator + fileName);
                            } else {
                                // this part may be the message content
                                messageContent = part.getContent().toString();
                            }
                        }

                        if (attachFiles.length() > 1) {
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                        }
                    } else if (contentType.contains("text/plain")
                            || contentType.contains("text/html")) {
                        Object content = message.getContent();
                        if (content != null) {
                            messageContent = content.toString();
                        }

                    // print out details of each message
//                System.out.println("Message #" + (i + 1) + ":");
//                System.out.println("\t From: " + from);
//                System.out.println("\t Subject: " + subject);
//                System.out.println("\t Sent Date: " + sentDate);
//                System.out.println("\t Message: " + messageContent);
//                System.out.println("\t Attachments: " + attachFiles);
                }
            }

            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for imap.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Runs this program with Gmail POP3 server
     */
    public static void main(String[] args) {
        String host = "imap.exmail.qq.com";
        String port = "993";
        String userName = "panbenxing@feellike21.com";
        String password = "Kiz2NJk6BjAn4KHk";

        String saveDirectory = "D:\\Attachment";

        EmailAttachmentReceiver receiver = new EmailAttachmentReceiver();
        receiver.setSaveDirectory(saveDirectory);
        receiver.downloadEmailAttachments(host, port, userName, password);

//        Date date = new Date("Wed Jul 26 11:15:13 CST 2017");
//        Date date1 = new Date();
//        boolean after = date1.after(date);
//        System.out.println(after);
//        System.out.println(date);
//        Instant end = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
//        Instant start = LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
//        Date date = new Date("Sun Jul 30 11:15:13 CST 2017");
//        if (date.before(Date.from(end)) && date.after(Date.from(start))) {
//            System.out.println("sdfsdfsf");
//        }
//        System.out.println(new Date());
//        System.out.println((Date.from(end)));
//        System.out.println((Date.from(start)));
    }

    public static void removeCryptographyRestrictions() {
        if (!isRestrictedCryptography()) {
            return;
        }
        try {
          /*
           * Do the following, but with reflection to bypass access checks:
           *
           * JceSecurity.isRestricted = false;
           * JceSecurity.defaultPolicy.perms.clear();
           * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
           */
            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

        } catch (final Exception e) {
        }
    }

    private static boolean isRestrictedCryptography() {
        // This simply matches the Oracle JRE, but not OpenJDK.
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }
}