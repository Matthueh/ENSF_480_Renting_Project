package userInterface;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.awt.*;
import java.util.ArrayList;
import databaseController.*;
import database.*;

public class UI implements ActionListener {
    // Maine JFrame to host all the panels
    private JFrame frame;

    /*
     * Each panel here correlates to a "page" in the GUI that UI can switch to
     * depending on the state the UI is in.
     */
    private JPanel mainPagePanel;
    private JPanel registrationPanel;
    private JPanel loginPanel;
    private JPanel registerPropertyPanel;
    private JPanel displaySummaryPanel;
    private JPanel setFeePanel;
    private JPanel setPeriodPanel;
    private JPanel setStatePanel;
    private JPanel sendEmailPanel;
    private JPanel sendPaymentPanel;
    private JPanel notificationPanel;
    private JPanel notificationSettingPanel;
    private JPanel browseNotificationsPanel;
    private JPanel unsubscribePanel;
    private JPanel browsePropertyPanel;
    private JPanel browsePropertyResult;
    private JPanel viewAccountsPanel;

    /*
     * accountType legend:
     * 0 - Not logged in
     * 1 - Registered Renter
     * 2 - Landlord
     * 3 - Manager
     */
    private int accountType;
    private String accountUsername;

    /*
     * These data members refer to controllers to help connect the UI to the
     * Database.
     */
    private LoginController loginController;
    private RegisterRenterController renterController;
    private RegisterLandlordController landlordController;
    private RegisterPropertyController propertyController;
    private StateController stateController; // Manager/landlord function controller
    private BrowsePropertyController browseProperty;
    private NotifyNewMatchListingsController notificationsController;
    private AccessAccountController accountController;

    /*
     * Components that multiple "pages" would use.
     */
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JButton backToMainButton;
    private JLabel typeLabel;
    private JLabel bedroomLabel;
    private JLabel bathroomLabel;
    private JLabel cityQuadrantLabel;
    private JLabel addressLabel;
    private JButton notificationButton;

    /*
     * Components for the main page.
     */
    private JButton registerAccountButton;
    private JButton loginButton;
    private JLabel accountInfo;
    private JButton signOutButton;
    private JButton registerPropertyButton;
    private JButton sendPaymentButton;
    private JButton viewAccountsButton;

    /*
     * Components for the registration page.
     */
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JButton registerRenterButton;
    private JButton registerLandlordButton;

    /*
     * Components for the login page.
     */
    private JButton loginToAccountButton;

    /*
     * Components for browseProperties
     */
    private JButton browsePropertiesButton;
    private JButton filterButton; // Search with filter
    private JButton filterButton2; // Search without filter

    // Components for find Browse Properties results Panel function
    // Can use the register property values
    private JButton browseSendEmailButton;
    private JButton nextButton;
    private JButton prevButton;
    private JButton backToFilterButton;

    /*
     * Components for the property registration page.
     */
    private JComboBox<String> typeComboBox;
    private JScrollPane typeScrollPane;
    private JTextField bedroomTextField;
    private JTextField bathroomTextField;
    private JCheckBox furnishedCheck;
    private JComboBox<String> cityComboBox;
    private JScrollPane cityQuadrantScrollPane;
    private JTextField addressTextField;
    private JButton registerPropertyButton2;

    // Components for Manager's page to direct to each function page
    private JButton displaySummaryButton;
    private JButton setPeriodButton;
    private JButton setFeeButton;
    private JButton setStateButton;

    // Components for display summary page to call function
    private JButton displaySummaryButton2;
    private JTextField summaryPeriodTextField;
    private JLabel summaryPeriodLabel;

    // Components for set state page to call function (manager & landlord)
    private JButton setStateButton2;
    private JTextField propertyIdTextField;
    private JLabel propertyIdLabel;
    private JLabel stateLabel;
    private JComboBox<String> stateComboBox;
    private JScrollPane stateScrollPane;
    private JTextArea eligiblePropertyIdTextArea;
    private JScrollPane eligibilePropertyIdScrollPane;

    // Components for set fee page to call function
    private JButton setFeeButton2;
    private JTextField setFeeTextField;
    private JLabel setFeeLabel;

    // Components for set period page to call function
    private JButton setPeriodButton2;
    private JTextField setPeriodTextField;
    private JLabel setPeriodLabel;

    /**
     * Components for the send email page
     */
    private JLabel propertyInfoLabel;
    private JLabel ownerEmailLabel;
    private JTextArea emailTextArea;
    private JScrollPane emailScrollPane;
    private JButton emailSendButton;

    // Component for renter's page - unsubscribe
    private JButton unsubscribeButton;

    // Components for unsubscribe page to call function
    private JButton unsubscribeButton2;
    private JTextField userTextField;
    private JLabel userLabel;
    private JTextField passTextField;
    private JLabel passLabel;

    /**
     * Components for the send payment page
     */
    private JLabel cardNumberLabel;
    private JTextField cardNumberTestField;
    private JLabel expirationDateLabel;
    private JTextField expirationDateTextField;
    private JLabel securityCodeLabel;
    private JTextField securityCodeTextField;
    private JLabel balanceLabel;
    private JTextField balanceTextField;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton makePaymentButton;

    /**
     * Components for main notifications panel.
     */
    private JButton notificationSettingsButton;
    private JButton browseNotificationButton;

    /**
     * Components for notification settings panel
     */ 
    private JButton setSettingsButton;

    /**
     * Components for browse notification panel
     */
    private JButton backToNotificationsButton;

    /**
     * Components for access accounts panel
     */
    private JTextField usernameAccessTextField;
    private JTextField passwordAccessTextField;
    private JTextField emailAccessTextField;
    private JLabel userTypeAccessLabel;
    private JTextField userTypeTextField;
    private JButton nextAccessButton;
    private JButton prevAccessButton;

    // ***** Constructor *****
    public UI() {
        frame = new JFrame("Rental Property Management System");
        frame.setVisible(true); // This is so we can actually see the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // This is so that the program actually terminates when
                                                              // closing the window.

        /*
         * Initialize the JPanels
         */
        mainPagePanel = new JPanel(new FlowLayout());
        registrationPanel = new JPanel(new FlowLayout());
        loginPanel = new JPanel(new FlowLayout());
        registerPropertyPanel = new JPanel(new GridLayout(0, 2));
        displaySummaryPanel = new JPanel(new FlowLayout());
        setStatePanel = new JPanel(new FlowLayout());
        setFeePanel = new JPanel(new FlowLayout());
        setPeriodPanel = new JPanel(new FlowLayout());
        sendEmailPanel = new JPanel();
        sendEmailPanel.setLayout(new BoxLayout(sendEmailPanel, BoxLayout.Y_AXIS));
        sendPaymentPanel = new JPanel(new GridLayout(0, 2));
        notificationPanel = new JPanel(new FlowLayout());
        notificationSettingPanel = new JPanel(new GridLayout(0, 2));
        browseNotificationsPanel = new JPanel(new GridLayout(0, 2)); 
        unsubscribePanel = new JPanel(new FlowLayout());
        browsePropertyPanel = new JPanel(new GridLayout(0, 2));
        browsePropertyResult = new JPanel(new GridLayout(0, 2));
        viewAccountsPanel = new JPanel(new GridLayout(0, 2));

        /*
         * Initialize the controllers and control data members.
         */
        loginController = new LoginController();
        renterController = new RegisterRenterController();
        landlordController = new RegisterLandlordController();
        propertyController = new RegisterPropertyController();
        stateController = new StateController();
        browseProperty = new BrowsePropertyController();
        notificationsController = new NotifyNewMatchListingsController();
        accountController = new AccessAccountController();
        accountType = 0;

        /*
         * Initialize the common components of multiple pages.
         */
        usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordTextField = new JTextField(20);
        backToMainButton = new JButton("Back");
        backToMainButton.addActionListener(this);
        typeLabel = new JLabel("Type:");
        bedroomLabel = new JLabel("Number of Bedrooms:");
        bathroomLabel = new JLabel("Number of Bathrooms:");
        cityQuadrantLabel = new JLabel("City Quadrant:");
        addressLabel = new JLabel("Address:");
        setStateButton = new JButton("Change State of Property"); // to direct to set state page (landlord & manager)
        setStateButton.addActionListener(this);

        /*
         * Initialize the components for the main page.
         */
        registerAccountButton = new JButton("Register new account");
        registerAccountButton.addActionListener(this);
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        accountInfo = new JLabel();
        signOutButton = new JButton("Sign Out");
        signOutButton.addActionListener(this);
        registerPropertyButton = new JButton("Register Property");
        registerPropertyButton.addActionListener(this);
        browsePropertiesButton = new JButton("Browse Properties");
        browsePropertiesButton.addActionListener(this);
        sendPaymentButton = new JButton("Send Payment");
        sendPaymentButton.addActionListener(this);
        notificationButton = new JButton("Notifications");
        notificationButton.addActionListener(this);
        viewAccountsButton = new JButton("View Accounts");
        viewAccountsButton.addActionListener(this);

        /*
         * Initialize the components for account registration
         */
        emailLabel = new JLabel("Email");
        emailTextField = new JTextField(20);
        registerRenterButton = new JButton("Register as Renter");
        registerRenterButton.addActionListener(this);
        registerLandlordButton = new JButton("Register as Landlord");
        registerLandlordButton.addActionListener(this);

        /*
         * Initialize the components for log in page
         */
        loginToAccountButton = new JButton("Log in");
        loginToAccountButton.addActionListener(this);

        /*
         * Initialize the components for register property page.
         */
        final DefaultComboBoxModel<String> houseTypes = new DefaultComboBoxModel<String>();
        houseTypes.addElement("");
        houseTypes.addElement("Apartment");
        houseTypes.addElement("Attached");
        houseTypes.addElement("Detached");
        houseTypes.addElement("Townhouse");
        typeComboBox = new JComboBox<String>(houseTypes);
        typeScrollPane = new JScrollPane(typeComboBox);

        bedroomTextField = new JTextField();
        bathroomTextField = new JTextField();
        furnishedCheck = new JCheckBox("Furnished");

        final DefaultComboBoxModel<String> cityQuadrants = new DefaultComboBoxModel<String>();
        cityQuadrants.addElement("");
        cityQuadrants.addElement("SW");
        cityQuadrants.addElement("NW");
        cityQuadrants.addElement("SE");
        cityQuadrants.addElement("NE");
        cityComboBox = new JComboBox<String>(cityQuadrants);
        cityQuadrantScrollPane = new JScrollPane(cityComboBox);

        addressTextField = new JTextField();
        registerPropertyButton2 = new JButton("Register Property");
        registerPropertyButton2.addActionListener(this);

        // Initialize components for Manager page to direct to each function page:
        displaySummaryButton = new JButton("Display Periodical Summary");
        displaySummaryButton.addActionListener(this);
        setFeeButton = new JButton("Change Monthly Fee");
        setFeeButton.addActionListener(this);
        setPeriodButton = new JButton("Change Monthly Period");
        setPeriodButton.addActionListener(this);

        // Initialize components for display summary page to call function
        displaySummaryButton2 = new JButton("Display Periodical Summary");
        displaySummaryButton2.addActionListener(this);
        summaryPeriodLabel = new JLabel("Monthly period");
        summaryPeriodTextField = new JTextField(20);

        // Initialize components for set state page to call function - Manager &
        // landlord
        setStateButton2 = new JButton("Change State of Property");
        setStateButton2.addActionListener(this);
        propertyIdLabel = new JLabel(("Property ID"));
        propertyIdTextField = new JTextField(20);
        stateLabel = new JLabel("State");
        final DefaultComboBoxModel<String> states = new DefaultComboBoxModel<String>();
        states.addElement("Active");
        states.addElement("Rented");
        states.addElement("Cancelled");
        states.addElement("Suspended");
        stateComboBox = new JComboBox<String>(states);
        stateScrollPane = new JScrollPane(stateComboBox);
        eligiblePropertyIdTextArea = new JTextArea(1, 20);
        eligiblePropertyIdTextArea.setEditable(false);
        eligibilePropertyIdScrollPane = new JScrollPane(eligiblePropertyIdTextArea);
        eligibilePropertyIdScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);;
        

        // Initialize components to call function in set fee page - Manager
        setFeeButton2 = new JButton("Change fee amount for properties");
        setFeeButton2.addActionListener(this);
        setFeeLabel = new JLabel("Fee");
        setFeeTextField = new JTextField(20);

        // Initialize components to call function in set period page - Manager
        setPeriodButton2 = new JButton("Change fee period for properties");
        setPeriodButton2.addActionListener(this);
        setPeriodLabel = new JLabel("Period");
        setPeriodTextField = new JTextField(20);

        // Initialize button on renter's page to direct to unsubscribe page
        unsubscribeButton = new JButton("Unsubscribe");
        unsubscribeButton.addActionListener(this);

        // Components for unsubscribe page to call function
        unsubscribeButton2 = new JButton("Unsubscribe");
        unsubscribeButton2.addActionListener(this);
        userTextField = new JTextField(20);
        userLabel = new JLabel("Username:");
        passTextField = new JTextField(20);
        passLabel = new JLabel("Password:");

        /**
         * Initialize Components for send email page
         */
        propertyInfoLabel = new JLabel();
        ownerEmailLabel = new JLabel();
        emailTextArea = new JTextArea();
        emailTextArea.setLineWrap(true);
        emailTextArea.setWrapStyleWord(true);
        emailScrollPane = new JScrollPane(emailTextArea);
        emailScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        emailScrollPane.setPreferredSize(new Dimension(250, 250));
        emailSendButton = new JButton("Send Email");
        emailSendButton.addActionListener(this);

        /**
         * Initialize components for send payment page
         */
        cardNumberLabel = new JLabel("Card Number:");
        cardNumberTestField = new JTextField(15);
        expirationDateLabel = new JLabel("Expiration Date (MM/DD/YYYY):");
        expirationDateTextField = new JTextField(10);
        securityCodeLabel = new JLabel("Security Code:");
        securityCodeTextField = new JTextField(3);
        balanceLabel = new JLabel("Current Balance:");
        balanceTextField = new JTextField();
        balanceTextField.setEditable(false);
        amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField(15);
        makePaymentButton = new JButton("Send Payment");
        makePaymentButton.addActionListener(this);

        // Initialize components for browse properties panel
        filterButton = new JButton("Search with filter");
        filterButton.addActionListener(this);
        filterButton2 = new JButton("Search without filter");
        filterButton2.addActionListener(this);

        // Initialize components to call function in display filtered properties
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        prevButton = new JButton("Prev");
        prevButton.addActionListener(this);
        browseSendEmailButton = new JButton("Send Email");
        browseSendEmailButton.addActionListener(this);
        backToFilterButton = new JButton("Back");
        backToFilterButton.addActionListener(this);

        /**
         * Components for main notifications panel.
         */
        notificationSettingsButton = new JButton("Settings");
        notificationSettingsButton.addActionListener(this);
        browseNotificationButton = new JButton("Browse");
        browseNotificationButton.addActionListener(this);

        /**
         * Components for notification settings panel
         */
        setSettingsButton = new JButton("Confirm Settings");
        setSettingsButton.addActionListener(this);

        /**
         * Components for browse notification panel
         */
        backToNotificationsButton = new JButton("Back");
        backToNotificationsButton.addActionListener(this);

        /**
         * Components for view accounts panel
         */
        usernameAccessTextField = new JTextField(20);
        usernameAccessTextField.setEditable(false);
        passwordAccessTextField = new JTextField(20);
        passwordAccessTextField.setEditable(false);
        emailAccessTextField = new JTextField(20);
        emailAccessTextField.setEditable(false);
        userTypeAccessLabel = new JLabel("User Type: ");
        userTypeTextField = new JTextField(20);
        userTypeTextField.setEditable(false);

        nextAccessButton =  new JButton("Next");
        nextAccessButton.addActionListener(this);
        prevAccessButton = new JButton("Prev");
        prevAccessButton.addActionListener(this);

        /*
         * Loads the main page and validates (a.k.a
         * update) the frame.
         */
        loadMainPagePanel();
        frame.pack();
        frame.validate();
    }

    // ***** Functions *****
    /*
     * Loads the components used for the main page into its panel and sets the panel
     * as the main frame's content.
     */
    private void loadMainPagePanel() {
        if (accountType == 0) { // Not logged in
            accountInfo.setText("Not Logged In");
            mainPagePanel.remove(signOutButton);
        } else { // Logged in
            if (accountType == 1) {
                accountInfo.setText("Renter: " + accountUsername);
            } else if (accountType == 2) {
                accountInfo.setText("Landlord: " + accountUsername);
            } else if (accountType == 3) {
                accountInfo.setText("Manager: " + accountUsername);
            }
            mainPagePanel.add(signOutButton);
        }
        mainPagePanel.add(accountInfo);
        // Checking whether to add the register account and log in buttons. This won't
        // be needed for a signed in account.
        if (accountType == 0) {
            mainPagePanel.add(registerAccountButton);
            mainPagePanel.add(loginButton);
        } else {
            mainPagePanel.remove(registerAccountButton);
            mainPagePanel.remove(loginButton);
        }
        if (accountType == 1) {
            mainPagePanel.add(unsubscribeButton);
            mainPagePanel.add(notificationButton);
        } else {
            mainPagePanel.remove(unsubscribeButton);
            mainPagePanel.remove(notificationButton);
        }
        mainPagePanel.add(browsePropertiesButton);
        // The buttons that would be seen if the user was signed in as a Landlord
        if (accountType == 2) {
            mainPagePanel.add(registerPropertyButton);
            mainPagePanel.add(setStateButton);
            mainPagePanel.add(sendPaymentButton);
        } else {
            mainPagePanel.remove(registerPropertyButton);
            mainPagePanel.remove(setStateButton);
            mainPagePanel.remove(sendPaymentButton);
        }

        // The buttons that would be seen if the user was signed in as a Manager
        if (accountType == 3) {
            mainPagePanel.add(displaySummaryButton);
            mainPagePanel.add(setFeeButton);
            mainPagePanel.add(setPeriodButton);
            mainPagePanel.add(viewAccountsButton);
        } else {
            mainPagePanel.remove(displaySummaryButton);
            mainPagePanel.remove(setFeeButton);
            mainPagePanel.remove(setPeriodButton);
            mainPagePanel.remove(viewAccountsButton);
        }

        // The buttons that would be seen if the user was signed in as a Manager OR
        // Landlord
        if (accountType == 2 || accountType == 3) {
            mainPagePanel.add(setStateButton);
        } else {
            mainPagePanel.remove(setStateButton);
        }

        frame.setContentPane(mainPagePanel);
    }

    /*
     * Loads the components used for the registration page into its panel and sets
     * the panel as the main frame's content.
     */
    private void loadRegistrationPanel() {
        registrationPanel.add(usernameLabel);
        usernameTextField.setText("");
        registrationPanel.add(usernameTextField);
        registrationPanel.add(passwordLabel);
        passwordTextField.setText("");
        registrationPanel.add(passwordTextField);
        registrationPanel.add(emailLabel);
        emailTextField.setText("");
        registrationPanel.add(emailTextField);
        registrationPanel.add(registerRenterButton);
        registrationPanel.add(registerLandlordButton);
        registrationPanel.add(backToMainButton);
        frame.setContentPane(registrationPanel);
    }

    /*
     * Loads the components used for the login page into its panel and sets
     * the panel as the main frame's content.
     */
    private void loadLoginPanel() {
        loginPanel.add(usernameLabel);
        usernameTextField.setText("");
        loginPanel.add(usernameTextField);
        loginPanel.add(passwordLabel);
        passwordTextField.setText("");
        loginPanel.add(passwordTextField);
        loginPanel.add(loginToAccountButton);
        loginPanel.add(backToMainButton);
        frame.setContentPane(loginPanel);
    }

    /**
     * Loads the components used for the register property panel and sets the panel
     * as the main frame's content.
     */
    private void loadRegisterPropertyPanel() {
        registerPropertyPanel.add(typeLabel);
        registerPropertyPanel.add(typeScrollPane);
        registerPropertyPanel.add(bedroomLabel);
        bedroomTextField.setText("");
        registerPropertyPanel.add(bedroomTextField);
        registerPropertyPanel.add(bathroomLabel);
        bathroomTextField.setText("");
        registerPropertyPanel.add(bathroomTextField);
        registerPropertyPanel.add(cityQuadrantLabel);
        registerPropertyPanel.add(cityQuadrantScrollPane);
        registerPropertyPanel.add(addressLabel);
        addressTextField.setText("");
        registerPropertyPanel.add(addressTextField);
        registerPropertyPanel.add(furnishedCheck);
        registerPropertyPanel.add(registerPropertyButton2);
        registerPropertyPanel.add(backToMainButton);
        frame.setContentPane(registerPropertyPanel);
    }

    /**
     * Loads components used for the display summary panel and sets the panel
     * as the main frame's content pane
     */
    private void loadDisplaySummaryPanel() {
        displaySummaryPanel.add(displaySummaryButton2);
        displaySummaryPanel.add(summaryPeriodLabel);
        summaryPeriodTextField.setText("");
        displaySummaryPanel.add(summaryPeriodTextField);
        displaySummaryPanel.add(backToMainButton);
        frame.setContentPane(displaySummaryPanel);
    }

    /**
     * Loads components that are used for the set state panel and sets the panel
     * as the main frame's content pane
     */
    private void loadSetStatePanel() {
        setStatePanel.add(setStateButton2);
        setStatePanel.add(stateLabel);
        setStatePanel.add(stateScrollPane);
        setStatePanel.add(propertyIdLabel);
        propertyIdTextField.setText("");
        setStatePanel.add(propertyIdTextField);
        if (accountType == 2) {
            StringBuilder eligibleIdString = new StringBuilder("Your properties -");
            ArrayList<Property> properties = browseProperty.getAllProperties();
            FilterOwnerController filter = new FilterOwnerController();
            properties = filter.filter(properties, accountUsername);
            for (int j = 0; j < properties.size(); j++) {
                if (j == 0) {
                    eligibleIdString.append(" " + properties.get(j).getID());
                } else {
                    eligibleIdString.append(", " + properties.get(j).getID());
                }
                eligibleIdString.append(": " + properties.get(j).getState());
                eligiblePropertyIdTextArea.setText(eligibleIdString.toString());
            }
        } else if (accountType == 3) {
            StringBuilder eligibleIdString = new StringBuilder("Properties -");
            ArrayList<Property> properties = browseProperty.getAllProperties();
            for (int j = 0; j < properties.size(); j++) {
                if (j == 0) {
                    eligibleIdString.append(" " + properties.get(j).getID());
                } else {
                    eligibleIdString.append(", " + properties.get(j).getID());
                }
                eligibleIdString.append(": " + properties.get(j).getState());
                eligiblePropertyIdTextArea.setText(eligibleIdString.toString());
            }
        }
        setStatePanel.add(eligibilePropertyIdScrollPane);
        setStatePanel.add(backToMainButton);
        frame.setContentPane(setStatePanel);
    }

    /**
     * Loads components that are used for the set fee panel and sets the panel
     * as the main frame's content pane
     */
    private void loadSetFeePanel() {
        setFeePanel.add(setFeeButton2);
        setFeePanel.add(setFeeLabel);
        setFeeTextField.setText("");
        setFeePanel.add(setFeeTextField);
        setFeePanel.add(backToMainButton);
        frame.setContentPane(setFeePanel);
    }

    /**
     * Loads components that are used for the display summary panel and sets the
     * panel
     * as the main frame's content pane
     */
    private void loadSetPeriodPanel() {
        setPeriodPanel.add(setPeriodButton2);
        setPeriodPanel.add(setPeriodLabel);
        setPeriodTextField.setText("");
        setPeriodPanel.add(setPeriodTextField);
        setPeriodPanel.add(backToMainButton);
        frame.setContentPane(setPeriodPanel);
    }

    /**
     * Loads components that are used for unsubscribe panel and sets the panel
     * as the main frame's content pane
     */
    private void loadUnsubscribePanel() {
        unsubscribePanel.add(unsubscribeButton2);
        unsubscribePanel.add(userLabel);
        userTextField.setText("");
        unsubscribePanel.add(userTextField);
        unsubscribePanel.add(passLabel);
        passTextField.setText("");
        unsubscribePanel.add(passTextField);
        unsubscribePanel.add(backToMainButton);
        frame.setContentPane(unsubscribePanel);
    }

    /**
     * Loads the components used for the send email panel and set it as the content
     * pane of the main frame.
     * 
     * @param property   - The property the email is related to.
     * @param ownerEmail - The email of the owner.
     */
    private void loadSendEmailPanel(Property property) {
        propertyInfoLabel
                .setText("<html>Type: " + property.getType() + "<br/>Number of Bedrooms: " + property.getBedrooms()
                        + "<br/>Number of Bathrooms: " + property.getBathrooms() + "<br/>Furnished: "
                        + property.getFurnished() + "<br/>CityQuadrant: " + property.getCityQuadrant() + "<br/>Owner: "
                        + property.getOwner() + "</html>");
        propertyInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sendEmailPanel.add(propertyInfoLabel);
        sendEmailPanel.add(ownerEmailLabel);
        emailTextArea.setText("");
        emailScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        sendEmailPanel.add(emailScrollPane);
        emailSendButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sendEmailPanel.add(emailSendButton);
        backToMainButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sendEmailPanel.add(backToMainButton);
        frame.setContentPane(sendEmailPanel);
    }

    /**
     * loads the components used for the send email panel and set it as the content
     * pane of the main frame.
     */
    private void loadSendPaymentPanel() {
        sendPaymentPanel.add(balanceLabel);
        sendPaymentPanel.add(balanceTextField);
        sendPaymentPanel.add(cardNumberLabel);
        sendPaymentPanel.add(cardNumberTestField);
        sendPaymentPanel.add(expirationDateLabel);
        sendPaymentPanel.add(expirationDateTextField);
        sendPaymentPanel.add(securityCodeLabel);
        sendPaymentPanel.add(securityCodeTextField);
        sendPaymentPanel.add(amountLabel);
        sendPaymentPanel.add(amountTextField);
        sendPaymentPanel.add(backToMainButton);
        sendPaymentPanel.add(makePaymentButton);
        frame.setContentPane(sendPaymentPanel);
    }

    private void loadBrowsePropertiesPanel() {
        browsePropertyPanel.add(typeLabel);
        browsePropertyPanel.add(typeScrollPane);
        browsePropertyPanel.add(bedroomLabel);
        bedroomTextField.setText("");
        browsePropertyPanel.add(bedroomTextField);
        browsePropertyPanel.add(bathroomLabel);
        bathroomTextField.setText("");
        browsePropertyPanel.add(bathroomTextField);
        browsePropertyPanel.add(cityQuadrantLabel);
        browsePropertyPanel.add(cityQuadrantScrollPane);
        browsePropertyPanel.add(furnishedCheck);
        browsePropertyPanel.add(filterButton);
        browsePropertyPanel.add(filterButton2);
        browsePropertyPanel.add(backToMainButton);
        frame.setContentPane(browsePropertyPanel);
    }

    // Might find a better way to do this
    int i;
    ArrayList<Property> property;

    /**
     * 
     * @param property will display 4 buttons
     *                 one button to go back
     *                 one button to go next
     *                 one button to go to email
     *                 one button to go to home page;
     */
    private void loadDisplayPropertyPanel(Property property) {
        propertyInfoLabel
                .setText("<html>Type: " + property.getType() + "<br/>Number of Bedrooms: " + property.getBedrooms()
                        + "<br/>Number of Bathrooms: " + property.getBathrooms() + "<br/>Furnished: "
                        + property.getFurnished() + "<br/>CityQuadrant: " + property.getCityQuadrant() + "<br/>Owner: "
                        + property.getOwner() + "</html>");
        propertyInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        browsePropertyResult.add(propertyInfoLabel);
        browsePropertyResult.add(browseSendEmailButton);
        emailSendButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        browsePropertyResult.add(nextButton);
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        browsePropertyResult.add(prevButton);
        prevButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        browsePropertyResult.add(backToFilterButton);
        backToMainButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        frame.setContentPane(browsePropertyResult);
    }

    /**
     * Loads the components used for the notifications main panel.
     */
    private void loadNotificationsPanel() {
        notificationPanel.add(notificationSettingsButton);
        notificationPanel.add(browseNotificationButton);
        notificationPanel.add(backToMainButton);
        frame.setContentPane(notificationPanel);
    }

    /**
     * Loads the compoenents used for the notification settings panel.
     */
    private void loadNotificationSettingsPanel() {
        notificationSettingPanel.add(typeLabel);
        notificationSettingPanel.add(typeScrollPane);
        notificationSettingPanel.add(bedroomLabel);
        bedroomTextField.setText("");
        notificationSettingPanel.add(bedroomTextField);
        notificationSettingPanel.add(bathroomLabel);
        bathroomTextField.setText("");
        notificationSettingPanel.add(bathroomTextField);
        notificationSettingPanel.add(cityQuadrantLabel);
        notificationSettingPanel.add(cityQuadrantScrollPane);
        notificationSettingPanel.add(furnishedCheck);
        notificationSettingPanel.add(setSettingsButton);
        notificationSettingPanel.add(backToNotificationsButton);
        frame.setContentPane(notificationSettingPanel);
    }

    /**
     * Loads the compoenents used for the browse notification panel.
     * @param property
     */
    private void loadBrowseNotificationPanel(Property property) {
        propertyInfoLabel
                .setText("<html>Type: " + property.getType() + "<br/>Number of Bedrooms: " + property.getBedrooms()
                        + "<br/>Number of Bathrooms: " + property.getBathrooms() + "<br/>Furnished: "
                        + property.getFurnished() + "<br/>CityQuadrant: " + property.getCityQuadrant() + "<br/>Owner: "
                        + property.getOwner() + "</html>");
        propertyInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        browseNotificationsPanel.add(propertyInfoLabel);
        browseNotificationsPanel.add(browseSendEmailButton);
        emailSendButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        browseNotificationsPanel.add(nextButton);
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        browseNotificationsPanel.add(prevButton);
        prevButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        browseNotificationsPanel.add(backToNotificationsButton);
        backToMainButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        frame.setContentPane(browseNotificationsPanel);
    }

    
    int a;
    ArrayList<Account> account;

    /**
     * Displays the loaded account information;
     */
    private void loadViewAccountPanel(Account acc) {
        usernameAccessTextField.setText(acc.getUsername());
        passwordAccessTextField.setText(acc.getPassword());
        emailAccessTextField.setText(acc.getEmail());
        userTypeTextField.setText(acc.getUserType());
        viewAccountsPanel.add(usernameLabel);
        viewAccountsPanel.add(usernameAccessTextField);
        viewAccountsPanel.add(passwordLabel);
        viewAccountsPanel.add(passwordAccessTextField);
        viewAccountsPanel.add(emailLabel);
        viewAccountsPanel.add(emailAccessTextField);
        viewAccountsPanel.add(userTypeAccessLabel);
        viewAccountsPanel.add(userTypeTextField);
        viewAccountsPanel.add(prevAccessButton);
        viewAccountsPanel.add(nextAccessButton);
        viewAccountsPanel.add(backToMainButton);
        frame.setContentPane(viewAccountsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerAccountButton) { // From main page to registration
            loadRegistrationPanel();
        } else if (e.getSource() == backToMainButton) { // Head back to main page.
            loadMainPagePanel();
        } else if (e.getSource() == loginButton) { // From main page to login
            loadLoginPanel();
        } else if (e.getSource() == registerRenterButton) { // Register as Renter in registration
            if (renterController.registerRenter(usernameTextField.getText(), passwordTextField.getText(),
                    emailTextField.getText())) {
                JOptionPane.showMessageDialog(frame, "Renter account successfully made.");
                accountType = 1;
                accountUsername = usernameTextField.getText();
                loadMainPagePanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Existing username or invalid inputs.", "Message",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == registerLandlordButton) { // Register as Landlord in registration
            if (landlordController.registerLandlord(usernameTextField.getText(), passwordTextField.getText(),
                    emailTextField.getText())) {
                JOptionPane.showMessageDialog(frame, "Landlord account successfully made.");
                accountType = 2;
                accountUsername = usernameTextField.getText();
                loadMainPagePanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Existing username or invalid inputs.", "Message",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == loginToAccountButton) { // Log into account.
            int loginResult = loginController.login(usernameTextField.getText(), passwordTextField.getText());
            if (loginResult == 0) { // Unable to find the account
                JOptionPane.showMessageDialog(frame, "Unable to login: Invalid credentials.", "Alert",
                        JOptionPane.WARNING_MESSAGE);
            } else { // Found an account
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                accountType = loginResult;
                accountUsername = usernameTextField.getText();
                loadMainPagePanel();
            }
        } else if (e.getSource() == signOutButton) { // To sign out of an account in the main page
            accountType = 0;
            accountUsername = "";
            loadMainPagePanel();
        } else if (e.getSource() == registerPropertyButton) { // From main page to property registration (Landlords
                                                              // only)
            loadRegisterPropertyPanel();
            JOptionPane.showMessageDialog(frame, "Please enter the details of the property to be registered.");
        } else if (e.getSource() == registerPropertyButton2) { // Registering a property
            String houseType = (String) typeComboBox.getSelectedItem();
            int bedrooms = Integer.parseInt(bedroomTextField.getText());
            int bathrooms = Integer.parseInt(bathroomTextField.getText());
            boolean furnished = furnishedCheck.isSelected();
            String cityQuadrant = (String) cityComboBox.getSelectedItem();
            String address = addressTextField.getText();
            propertyController.registerProperty(houseType, bedrooms, bathrooms, furnished, cityQuadrant,
                    accountUsername, address);
            JOptionPane.showMessageDialog(frame, "Property registered under " + accountUsername + ".");
        } else if (e.getSource() == displaySummaryButton) { // Asks for monthly period to make summary
            loadDisplaySummaryPanel();
            JOptionPane.showMessageDialog(frame, "Please enter the period (in months from today) to display the period summary report.");
        } else if (e.getSource() == displaySummaryButton2) { // Displays summary
            int period = Integer.parseInt(summaryPeriodTextField.getText());
            JOptionPane.showMessageDialog(frame, stateController.displayPeriodicalSummary(period));
        } else if (e.getSource() == setStateButton) { // From main page to set state panel
            loadSetStatePanel();
            JOptionPane.showMessageDialog(frame, "Please enter the propertyID and its new state.");
        } else if (e.getSource() == setStateButton2) { // Set state of property
            String state = (String) stateComboBox.getSelectedItem();
            int propertyID = Integer.parseInt(propertyIdTextField.getText());
            stateController.setState(state, propertyID);
            JOptionPane.showMessageDialog(frame, "State with ID: " + propertyID + " changed to " + state + ".");
            loadSetStatePanel();
        } else if (e.getSource() == setFeeButton) { // From main page to set fee amount panel
            loadSetFeePanel();
            JOptionPane.showMessageDialog(frame, "Please enter the new fee amount for each new registered property.");
        } else if (e.getSource() == setFeeButton2) { // Set fee amount
            int fee = Integer.parseInt(setFeeTextField.getText());
            stateController.setFeeAmount(fee);
            JOptionPane.showMessageDialog(frame, "Fee amount has been set.");
        } else if (e.getSource() == setPeriodButton) { // From main page to set fee period panel
            loadSetPeriodPanel();
            JOptionPane.showMessageDialog(frame, "Please enter the new fee period for each new registered property.");
        } else if (e.getSource() == setPeriodButton2) { // Set fee period
            int period = Integer.parseInt(setPeriodTextField.getText());
            stateController.setFeePeriod(period);
            JOptionPane.showMessageDialog(frame, "Fee amount has been set.");
        } else if (e.getSource() == emailSendButton) { // "Sending" an email
            JOptionPane.showMessageDialog(frame, "Email sent.");
            loadMainPagePanel();
        } else if (e.getSource() == unsubscribeButton) { // From main page to unsubscribe
            JOptionPane.showMessageDialog(frame, "Please enter your username and password to unsubscribe.");
            loadUnsubscribePanel();
        } else if (e.getSource() == unsubscribeButton2) { // unsubscribe account
            String user = userTextField.getText();
            String pass = passTextField.getText();
            if (user.equals(accountUsername) && accountType == 1)
            {
                renterController.unsubscribeRenter(user, pass);
                accountType = 0;
                accountUsername = "";
                JOptionPane.showMessageDialog(frame, "Successfully unsubscribed.");
                loadMainPagePanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect credentials.", "Message", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == sendPaymentButton) { // From main page to send payment panel
            loadSendPaymentPanel();
        } else if (e.getSource() == makePaymentButton) { // Sending the payment in send payment panel
            JOptionPane.showMessageDialog(frame, "Payment has been sent.");
        } else if (e.getSource() == browsePropertiesButton) { // From main page to browse property panel
            loadBrowsePropertiesPanel();
            JOptionPane.showMessageDialog(frame, "Please enter the details of the properties you would like to find.");
        } else if (e.getSource() == filterButton) { // Using filter to get browse results
            // This part makes sure that it is the right data type going into the filters
            String houseType = (String) typeComboBox.getSelectedItem();
            int bedrooms = Integer.parseInt(bedroomTextField.getText());
            int bathrooms = Integer.parseInt(bathroomTextField.getText());
            boolean furnished = furnishedCheck.isSelected();
            String cityQuadrant = (String) cityComboBox.getSelectedItem();

            // This will filter it
            browseProperty.setMatchedListing(houseType, bedrooms, bathrooms, furnished, cityQuadrant, "", "Active", 0);
            property = browseProperty.getFilteredProperties();
            if (property.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No property matches your criteria.", "Message", JOptionPane.WARNING_MESSAGE);
            } else {
                i = 0;
                // Display the first property panel
                loadDisplayPropertyPanel(property.get(i));
            }
            // We will make four buttons
        } else if (e.getSource() == filterButton2) { // viewing all properties
            property = browseProperty.getAllProperties();
            if (property.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No properties are saved in our database.", "Message", JOptionPane.WARNING_MESSAGE);
            } else {
                i = 0;
                loadDisplayPropertyPanel(property.get(i));
            }
        } else if (e.getSource() == nextButton && i < property.size() - 1) { // Next property
            // Then you want to switch the next property by incrementing global variable
            // then send the variable into the browsepropertyPanel
            i++;
            if (frame.getContentPane() == browsePropertyResult) {
                loadDisplayPropertyPanel(property.get(i));
            } else if (frame.getContentPane() == browseNotificationsPanel) {
                loadBrowseNotificationPanel(property.get(i));
            }
        } else if (e.getSource() == prevButton && i > 0) { // Previous property
            // if i < 0 then the
            // Then you want to switch back to the next property by decrementing global
            // variable
            i--;
            if (frame.getContentPane() == browsePropertyResult) {
                loadDisplayPropertyPanel(property.get(i));
            } else if (frame.getContentPane() == browseNotificationsPanel) {
                loadBrowseNotificationPanel(property.get(i));;
            }
        } else if (e.getSource() == backToFilterButton) { // Return from browse properties result to search criteria
            loadBrowsePropertiesPanel();
        } else if (e.getSource() == browseSendEmailButton) { // Sending an email while browsing
            loadSendEmailPanel(property.get(i));
        } else if (e.getSource() == notificationButton) { // From main page to notifications panel
            loadNotificationsPanel();
        } else if (e.getSource() == notificationSettingsButton) { // From notifications panel to notifications settings 
            JOptionPane.showMessageDialog(frame, "Please input your preferences for properties");
            loadNotificationSettingsPanel();
        } else if (e.getSource() == setSettingsButton) { // Set preferences for registered renter currently logged in 
            String houseType = (String) typeComboBox.getSelectedItem();
            int bedrooms = Integer.parseInt(bedroomTextField.getText());
            int bathrooms = Integer.parseInt(bathroomTextField.getText());
            boolean furnished = furnishedCheck.isSelected();
            String cityQuadrant = (String) cityComboBox.getSelectedItem();
            try {
                notificationsController.modifyNotifications(accountUsername, houseType, bedrooms, bathrooms, cityQuadrant, furnished);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(frame, "Preferences Set");
        } else if (e.getSource() == browseNotificationButton) { // From notifications panel to browse notifications 
            try {
                String houseType = notificationsController.retrieveDesiredType(accountUsername);
                int bedrooms = notificationsController.retrieveDesiredBedrooms(accountUsername);
                int bathrooms = notificationsController.retrieveDesiredBathrooms(accountUsername);
                String owner = "";
                String state = "Active";
                boolean furnished = notificationsController.retrieveDesiredFurnishing(accountUsername);
                String cityQuadrant = notificationsController.retrieveDesiredCityQuadrant(accountUsername);

                // This will filter it
                browseProperty.setMatchedListing(houseType, bedrooms, bathrooms, furnished, cityQuadrant, owner, state, 0);
                property = browseProperty.getFilteredProperties();
                if (!property.isEmpty()) {
                    i = 0;
                    // Display the first property panel
                    loadBrowseNotificationPanel(property.get(i));
                } else {
                    JOptionPane.showMessageDialog(frame, "No active listings match your preferences.", "Message", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == backToNotificationsButton) { // Going to notifications panel
            loadNotificationsPanel();
        } else if (e.getSource() == viewAccountsButton) { // Viewing accounts
            account = accountController.retrieveAccounts();
            if (account.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "There are no accounts in the database");
            } else {
                a = 0;
                loadViewAccountPanel(account.get(a));
            }
        } else if (e.getSource() == nextAccessButton && a < account.size() - 1) { // Next account
            a++;
            loadViewAccountPanel(account.get(a));
        } else if (e.getSource() == prevAccessButton && a > 0) { // Prev account
            a--;
            loadViewAccountPanel(account.get(a));
        }

        frame.pack();
        frame.validate();
    }

}
