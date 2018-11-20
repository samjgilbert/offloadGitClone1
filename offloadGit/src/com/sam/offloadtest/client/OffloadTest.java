package com.sam.offloadtest.client; //cloned version

import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty.Properties;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationCallback;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.event.NodeDragEndEvent;
import com.ait.lienzo.client.core.event.NodeDragEndHandler;
import com.ait.lienzo.client.core.event.NodeDragMoveEvent;
import com.ait.lienzo.client.core.event.NodeDragMoveHandler;
import com.ait.lienzo.client.core.event.NodeDragStartEvent;
import com.ait.lienzo.client.core.event.NodeDragStartHandler;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseDoubleClickHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.DragBounds;
import com.ait.lienzo.client.core.types.LinearGradient;
import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.client.widget.DragContext;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragConstraint;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OffloadTest implements EntryPoint {
	private int nTrials = 17; 
    private String IDexperimentCode = "optimal_mTurk"; //which database to check for participant codes
    private String adminID = "Asam"; //WorkerID for admin access
    private String workerId = com.google.gwt.user.client.Window.Location.getParameter("workerId");
    private String testCode = com.google.gwt.user.client.Window.Location.getParameter("test");
    private String participantID = "";
    private String startPos = com.google.gwt.user.client.Window.Location.getParameter("startPos");
    private String phpOutput = null;
    private int[] position = {1, 1};
    private int circleRadius = 15;
    private int boxSize = 0;
    private int margin = 0;
    private int nextCircle = 0;
    private int exitFlag = 0;
    private int pauseDur = 500;
    private boolean flashFlag = false;
    private int clickedCircle = 0;
    final private int FORWARD = 0;
    final private int REVERSE = 1;
    private Date blockStart;
    private Date dragStart;
    private Date dragEnd;
    BlockParams blockParams = new BlockParams();
    TrialParams trialParams = new TrialParams();
    private int trialNum = 0;
    private Date instructStart = new Date();
    private Date instructEnd = new Date();
    private int minimumWindowSize = 500;
    private boolean quitFlag = false;
    private List<Integer> trialList = new ArrayList<Integer>();
    private boolean male = false;
    private boolean female = false;
    private boolean other = false;
    private String age = "99";
    private String rewardCode = "";
    private int c; //use this as a counter
    private double stimSize = 0.35; //proportion of full screen height/width
    ProgressBar progress = new ProgressBar(0, nTrials);
    final HorizontalPanel progressPanel = new HorizontalPanel();
    final HorizontalPanel progressHorizontalPanel = new HorizontalPanel();
    private int quizCircle = -1;
    private int sliderValue = -1;
    private int slider1, slider2;
    int testing = 1;
    private int instructCondition = Random.nextInt(2);
    private int targetCorrect = 0;
    private int targetError = 0;
    private int checkExit = 1;
    private int halfWayError;
    private int halfWayCorrect;
    private int completedCircles, nTargets, nHits, circleAdjust, reminderCompletedCircles, backupReminderFlag, backupCompletedCircles;
    private ColorName[] circleColours = new ColorName[4];

    private boolean showPoints = false;
    private boolean checkSaved = false;
    private int targetValue = 10;
    private int currentValue = 0;
    private int offloadCost = -5;
    private int totalPoints = 0;
    private int reminderFlag = -1;
    private int[] conditions = new int[nTrials];
    private int points;
    int counterbalancing = Random.nextInt(2);
    private int noReminder = 1;
    private int timeCounter=1, trialTimeCounter=1;
    private int minutes=0, seconds=0;
    Label timerLabel = new Label(" 3:00");
    Timer trialTimer;

    private boolean showInstructions = false;

    //digit span
    String db = "";
    int digitMax = 3;
    int digitRate = 2000;
    int trialNo = 0;
    int spanPos;
    int spanTrials = 14;
    int span = 3;
    Boolean lastCorrect = true;
    Boolean readyForInput = false;
    String spanString = "";
    String typedSpan = "";
    
    private boolean showFeedback = false;
    
    int difficultyGroup = Random.nextInt(2);
    int feedbackGroup = Random.nextInt(2);
    
    int EASY = 0;
    int DIFFICULT = 1;
    int POSITIVE = 0;
    int NEGATIVE = 1;
    
    int groupCondition=-1;
    int buttonGroup=-1;
    
    private int pracTargets;
    private int order = Random.nextInt(2); // order of reminder / no remionder options on screen
    private int colourCounterbalance = Random.nextInt(2); //assignment of buttons to pink / green colours
    
    int lockOutTime = 5000;
    
	public void onModuleLoad() {
        groupCondition = (2*difficultyGroup)+feedbackGroup;
        //0 = easy positive
        //1 = easy negative
        //2 = difficult positive
        //3 = difficult negative
        
        buttonGroup = (2*order)+colourCounterbalance;

        trialTimer = new Timer() {
            public void run() {
                minutes=trialTimeCounter/60;
                seconds=trialTimeCounter % 60;
                
                if (seconds<10) {
                    timerLabel.setText(" "+minutes+":"+"0"+seconds);
                } else {
                    timerLabel.setText(" "+minutes+":"+seconds);
                }
                        
                trialTimeCounter--;
                
                if (trialTimeCounter<0) {
                    trialTimeCounter=0;
                    timerLabel.addStyleName("redColour");
                    cancel();
                }
            }
        };

        circleColours[0] = ColorName.YELLOW;
        circleColours[1] = ColorName.DEEPSKYBLUE;
        circleColours[2] = ColorName.VIOLET;
        circleColours[3] = ColorName.CORAL;

        try {
            position[0] = Integer.parseInt(startPos);
        } catch (Throwable t) {
            position[0] = 1;
        }

        try {
            testing = Integer.parseInt(testCode);
        } catch (Throwable t) {
            testing = 0;
        }

        int valueA, valueB;

        if (counterbalancing == 0) {
            valueA = targetValue;
            valueB = 0;
        } else {
            valueA = 0;
            valueB = targetValue;
        }

        List<Integer> values = new ArrayList<Integer>();

        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(5);
        values.add(6);
        values.add(7);
        values.add(8);
        values.add(9);

        for (int i = 0; i < values.size(); i++) {
            Collections.swap(values, i, Random.nextInt(values.size()));
        }

        conditions[0] = values.get(0);;
        conditions[1] = valueA;
        conditions[2] = values.get(1);
        conditions[3] = valueB;
        conditions[4] = values.get(2);
        conditions[5] = valueA;
        conditions[6] = values.get(3);
        conditions[7] = valueB;
        conditions[8] = values.get(4);
        conditions[9] = valueA;
        conditions[10] = values.get(5);
        conditions[11] = valueB;
        conditions[12] = values.get(6);
        conditions[13] = valueA;
        conditions[14] = values.get(7);
        conditions[15] = valueB;
        conditions[16] = values.get(8);
        
        sequenceHandler();
    }

	public void sequenceHandler() {
        switch (position[0]) {
            case 1:
                blockParams.alphabetOrder = FORWARD;
                position[1] = 0;
                trialNum = 0;
                blockParams.blockNum = 2;
                blockParams.nTrials = 1000;
                blockParams.nCircles = 6;
                blockParams.totalCircles = 25;
                blockParams.defaultExit = 4;
                blockParams.totalTargets = 10;
                blockParams.nPM = 0;
                blockParams.cuedTargets = false;
                blockParams.showClock = false;
                blockParams.moveAny = true;
                blockParams.pmTiming = 0;

                trialHandler();
                break;        
        }
    }
    
    public void generateRewardCode() {
        final HTML workerError = new HTML("You must accept the HIT before you can continue. "
                + "Please close this page, accept the HIT, and try again."
                + "<br><br>If you have already accepted the HIT there has been a JavaScript "
                + "error which means you will not be able to continue. Apologies.");

        if (workerId.startsWith("A")) {
            //generate reward code
            for (int i = 0; i < 7; i++) {
                rewardCode = rewardCode + (Random.nextInt(9) + 1);
            }

            //save reward code to database
            String phpString = "rewardCode.php?code=" + rewardCode + "&workerId=" + workerId;

            phpCall(phpString);

            position[0]++;
            sequenceHandler();
        } else {
            RootPanel.get().add(workerError);
        }

    }

    public void getParticipantID() {
        final VerticalPanel panel = new VerticalPanel();
        final Label instructLabel = new Label("Please enter your Mechanical Turk worker ID below:");
        final TextBox textBox = new TextBox();
        final Button button = new Button("Submit");

        final HTML workerError = new HTML("You must accept the HIT before you can continue. "
                + "Please close this page, accept the HIT, and try again."
                + "<br><br>If you have already accepted the HIT there has been a JavaScript "
                + "error which means you will not be able to continue. Apologies.");

        panel.add(instructLabel);
        panel.add(textBox);
        panel.add(button);

        try {
            if (workerId.startsWith("A")) {
                RootPanel.get().add(panel);
            } else {
                String phpString = "rewardCode.php?participantID=";
                phpString = phpString + participantID + "&code=99999&workerId=" + workerId;

                phpCall(phpString);

                RootPanel.get().add(workerError);
            }
        } catch (Throwable t) {
            String phpString = "rewardCode.php?participantID=";
            phpString = phpString + participantID + "&code=99999&workerId=99999";

            phpCall(phpString);

            RootPanel.get().add(workerError);
        }

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (textBox.getText().length() == 0) {
                    instructLabel.setStyleName("red");
                } else {
                    participantID = textBox.getText();

                    //generate reward code
                    for (int i = 0; i < 7; i++) {
                        rewardCode = rewardCode + (Random.nextInt(9) + 1);
                    }

                    //save reward code to database
                    String phpString = "rewardCode.php?participantID=";
                    phpString = phpString + participantID + "&code=" + rewardCode + "&workerId=" + workerId;

                    phpCall(phpString);

                    RootPanel.get().remove(panel);

                    position[0]++;
                    sequenceHandler();
                }
            }
        });
    }

    public void digitSpan() {
       
        db = "digitSpan";
        trialNo = 0;
        span = 1;

        final VerticalPanel wrapper = new VerticalPanel();

        wrapper.setWidth(Window.getClientWidth() + "px");
        wrapper.setHeight((Window.getClientHeight() * 0.6) + "px");
        wrapper.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        wrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        wrapper.add(progress);

        wrapper.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        final FocusPanel focusPanel = new FocusPanel();

        spanPos = 0;
        final Label digit = new Label("");
        digit.addStyleName("digit");

        spanString = generateSpanString(span, 3);

        focusPanel.add(digit);
        wrapper.add(focusPanel);
        RootPanel.get().add(wrapper);
        focusPanel.setFocus(true);

        digit.setText(spanString);
        readyForInput = false;

        focusPanel.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
                if (readyForInput) {
                    if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
                        if (typedSpan.length() > 0) {
                            typedSpan = typedSpan.substring(0, typedSpan.length() - 1);

                            if (typedSpan.length() > 0) {
                                digit.setText(typedSpan);
                            } else {
                                digit.setText("?");
                            }
                        }
                    }

                    if ((event.getNativeKeyCode() > 95) & (event.getNativeKeyCode() < 106)) {
                        typedSpan = typedSpan + (event.getNativeKeyCode() - 96);
                        digit.setText(typedSpan);

                        if (typedSpan.length() == spanString.length()) {
                            if (typedSpan.equals(spanString)) {
                            //data = trialNo + "," + spanString + "," + typedSpan + "," + span + "," + "1";
                                //outputData();

                                digit.addStyleName("green");
                                lastCorrect = true;

                                if (span > digitMax) {
                                    digitMax = span;
                                }

                                span++;
                            } else {
                            //data = trialNo + "," + spanString + "," + typedSpan + "," + span + "," + "0";
                                //outputData();

                                digit.addStyleName("red");

                                if (!lastCorrect) {
                                    if (span > 3) {
                                        span--;
                                    }

                                    lastCorrect = true; // set to true after
                                    // decreasing
                                    // the span, so two
                                    // incorrect
                                    // answers required for next
                                    // decrease
                                } else {
                                    lastCorrect = false;
                                }
                            }

                            new Timer() {
                                public void run() {
                                    digit.setText("");
                                    digit.removeStyleName("red");
                                    digit.removeStyleName("green");

                                    if (trialNo++ < spanTrials) {
                                        new Timer() {
                                            public void run() {
                                                spanString = generateSpanString(span, 3);
                                                spanPos = 0;
                                                digit.setText(spanString);
                                                typedSpan="";
                                                readyForInput=false;
                                            }
                                        }.schedule(500);
                                    } else {
                                        //allData[1] = digitMax;

                                        RootPanel.get().clear();
                                        position[0]++;
                                        sequenceHandler();
                                    }
                                }
                            }.schedule(500);
                        }
                    }
                } else {
                    readyForInput = true;
                    digit.setText("?");
                }
            }
        });
    }

    String generateSpanString(int nDigits, int minDistance) {
        // minDistance is the minimum distance between two adjacent digits
        String s = "";
        Boolean ok = false;
        Boolean[] used = new Boolean[10];

        used[0] = true;

        while (!ok) {
            ok = true;
            s = "";

            while (s.length() < nDigits) {
                // add a random permutation of 1-9
                for (int i = 1; i < 10; i++) {
                    used[i] = false;
                }

                for (int i = 1; i < 10; i++) {
                    int d = 0;

                    while (used[d]) {
                        d = (Random.nextInt(9) + 1);
                    }

                    used[d] = true;

                    s = s + d;
                }
            }

            // now trim to desired length
            s = s.substring(0, nDigits);

            // now check adjacent digits
            for (int i = 1; i < nDigits; i++) {
                int a = s.charAt(i - 1);
                int b = s.charAt(i);

                // diff squared less than 5:
                if (Math.abs(a - b) < minDistance) {
                    ok = false;
                }

            }
        }

        return (s);
    }
    
    public void addParticipantID() {
        String phpString = "addID.php?participantID=" + workerId + "&status=" + groupCondition + "&buttonGroup=" + buttonGroup;

        phpCall(phpString);
        
        phpString = "addIP.php";
        phpCall(phpString);
    }
    
    public void finishParticipantID() {
        String phpString = "updateID.php?participantID=" + workerId + "&status=4";
        phpCall(phpString);
    }
    
    public void getButtonGroup() {
        String phpString = "buttonGroup.php?participantID=" + workerId;

        phpCall(phpString);

        timeCounter = 1;

        new Timer() {
            public void run() {
                timeCounter++;

                if (phpOutput.equals("0")) {
                    cancel();
                    
                    order=0;
                    colourCounterbalance=0;

                    position[0]++;
                    sequenceHandler();
                }
                
                if (phpOutput.equals("1")) {
                    cancel();
                    
                    order=0;
                    colourCounterbalance=1;

                    position[0]++;
                    sequenceHandler();
                }
                
                if (phpOutput.equals("2")) {
                    cancel();
                    
                    order=1;
                    colourCounterbalance=0;

                    position[0]++;
                    sequenceHandler();
                }
                
                if (phpOutput.equals("3")) {
                    cancel();
                    
                    order=1;
                    colourCounterbalance=1;

                    position[0]++;
                    sequenceHandler();
                }

                if (timeCounter == 70) {
                    cancel();
                    Window.alert("Database connection error. Please check your internet connection and try again");
                }
            }
        }.scheduleRepeating(100); 
    }
    
    public void checkWorkerID() {
        String phpString = "checkID.php?participantID=" + workerId;

        phpCall(phpString);

        timeCounter = 1;

        new Timer() {
            public void run() {
                timeCounter++;

                if (phpOutput.equals("-1")) {
                    cancel();
                    
                    addParticipantID(); //record this participant ID in database and note counterbalancing settings
                    
                    position[0]++;
                    sequenceHandler();
                }

                if (phpOutput.equals("0")) {
                    cancel();
                    
                    difficultyGroup = EASY;
                    feedbackGroup = POSITIVE;
                    
                    getButtonGroup();
                }

                if (phpOutput.equals("1")) {
                    cancel();
                    
                    difficultyGroup = EASY;
                    feedbackGroup = NEGATIVE;
                    
                    getButtonGroup();
                }

                if (phpOutput.equals("2")) {
                    cancel();
                    
                    difficultyGroup = DIFFICULT;
                    feedbackGroup = POSITIVE;
                    
                    getButtonGroup();
                }

                if (phpOutput.equals("3")) {
                    cancel();
                    
                    difficultyGroup = DIFFICULT;
                    feedbackGroup = NEGATIVE;
                    
                    getButtonGroup();
                }

                if (phpOutput.equals("4")) {
                    cancel();
                    
                    RootPanel.get().add(new Label("Your mTurk ID has already been used to complete this experiment. This means that you will not be able to take part again"));
                }

                if (timeCounter == 70) {
                    cancel();
                    Window.alert("Database connection error. Please check your internet connection and try again");
                }
            }
        }.scheduleRepeating(100);
    }

    public void getWorkerID() {
        final Label versionLabel = new Label("Version 1. 14 October 2018.");
        final Label label = new Label("Participant ID: ");
        final TextBox textBox = new TextBox();
        final Button goButton = new Button("go");

        final HorizontalPanel horizontalPanel = new HorizontalPanel();

        horizontalPanel.add(label);
        horizontalPanel.add(textBox);

        RootPanel.get().add(versionLabel);
        RootPanel.get().add(horizontalPanel);
        RootPanel.get().add(goButton);

        goButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                workerId = textBox.getText();

                String phpString = "checkID.php?participantID=" + workerId;

                phpCall(phpString);
                
                timeCounter=1;

                new Timer() {
                    public void run() {
                        timeCounter++;

                        if ((phpOutput.equals("OK")) | checkSaved == false) {
                            cancel();
                            RootPanel.get().clear();
                            position[0]++;
                            sequenceHandler();
                        }
                        
                        if ((phpOutput.equals("Used")) | checkSaved == false) {
                            cancel();
                            Window.alert("This participant ID has already been used. Please choose another ID");
                        }
                        
                        if (timeCounter==50) {
                            cancel();
                            Window.alert("Database connection error. Please check your internet connection and try again");
                        }
                    }
                }.scheduleRepeating(100);
            }
        });
    }

    public void checkScreenSize() {
        int xDim = Window.getClientWidth();
        int yDim = Window.getClientHeight();

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final VerticalPanel verticalPanel = new VerticalPanel();
        final Button continueButton = new Button("Try Again");

        String HTMLtext = "Your browser window is too small. You will not "
                + "be able to continue unless you make the window larger.<br><br>"
                + "Please maximise the size of your window and click the button below "
                + "to try again.<br><br>";

        final HTML textHTML = new HTML(HTMLtext);

        horizontalPanel.setWidth(xDim + "px");
        horizontalPanel.setHeight(yDim + "px");

        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        verticalPanel.setWidth("75%");
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        verticalPanel.add(textHTML);
        verticalPanel.add(continueButton);

        horizontalPanel.add(verticalPanel);

        continueButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(horizontalPanel);

                new Timer() {
                    public void run() {
                        checkScreenSize();
                    }
                }.schedule(500);
            }
        });

        if ((xDim > minimumWindowSize) & (yDim > minimumWindowSize)) {
            position[0]++;
            sequenceHandler();
        } else {
            RootPanel.get().add(horizontalPanel);
        }
    }

    public void trialInstructions() {
        final Date instructionStart = new Date();

        reminderFlag = -1;
        backupReminderFlag = -1;
        reminderCompletedCircles = -999;

        final HTML displayText = new HTML();
        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final VerticalPanel verticalPanel = new VerticalPanel();
        final HorizontalPanel buttonPanel = new HorizontalPanel();
        String displayString = "You have scored a total of " + totalPoints + " points so far.<br><br>";

        if (points == 0) {
            displayString = displayString + "This time you must do the task without setting any reminders.<br><br>"
                    + "Please touch the button below to start.";
        } else if (points == targetValue) {
            displayString = displayString + "This time you <b>must</b> set a reminder for every special circle.<br><br>"
                    + "Please touch the button below to start.";
        } else {
            displayString = displayString + "This time you have a choice.<br><br>Please touch the option that you prefer.<br><br>";
        }

        displayText.setHTML(displayString);

        final Button reminderButton = new Button("Special circles worth<br><b>" + points + " </b>points<br><br>"
                + "Reminders allowed");

        if (points == targetValue) {
            reminderButton.setHTML("Special circles worth<br><b>" + points + " </b>points<br><br>"
                    + "You <b>must</b> set reminders");
        }

        final Button noReminderButton = new Button("Special circles worth<br><b>" + targetValue + " </b>points<br><br>"
                + "Reminders <b>not</b> allowed");

        if (colourCounterbalance==0) {
            reminderButton.setStyleName("pinkButton");
            noReminderButton.setStyleName("greenButton");
        } else {
            reminderButton.setStyleName("greenButton");
            noReminderButton.setStyleName("pinkButton");
        }

        if (order == 0) {
            if (points > 0) {
                buttonPanel.add(reminderButton);
            }

            if (points < targetValue) {
                buttonPanel.add(noReminderButton);
            }
        } else {
            if (points < targetValue) {
                buttonPanel.add(noReminderButton);
            }

            if (points > 0) {
                buttonPanel.add(reminderButton);
            }
        }

        //set up vertical panel
        verticalPanel.setWidth("75%");
        //verticalPanel.setHeight(Window.getClientHeight() + "px");
        verticalPanel.setHeight("300px");

        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        //add elements to panel
        displayText.setStyleName("instructionText");
        verticalPanel.add(displayText);
        verticalPanel.add(buttonPanel);

        //place vertical panel inside horizontal panel, so it can be centred
        horizontalPanel.setWidth(Window.getClientWidth() + "px");
        horizontalPanel.setHeight(Window.getClientHeight() + "px");

        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        horizontalPanel.add(verticalPanel);

        //add panel to root
        RootPanel.get().add(horizontalPanel);

        if ((points > 0) & (points < targetValue)) {
            reminderButton.setWidth(noReminderButton.getOffsetWidth() + "px");
        }

        reminderButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                noReminder = 0;

                Date responseTime = new Date();

                String phpString = "preTrial.php?participantID=";
                phpString = phpString + workerId + "&trialNum=" + trialNum + "&rewardValue=" + points;
                phpString = phpString + "&assignment=" + order + "&choice=0&RT=";
                phpString = phpString + ((int) (responseTime.getTime() - instructionStart.getTime()));

                if (showInstructions) {
                    phpCall(phpString);
                }

                RootPanel.get().remove(horizontalPanel);

                blockParams.moveAny = true;
                currentValue = points;

                new Timer() {
                    public void run() {
                        position[1]++;
                        trialHandler();
                    }
                }.schedule(pauseDur);
            }
        });

        noReminderButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                noReminder = 1;

                Date responseTime = new Date();

                String phpString = "preTrial.php?participantID=";
                phpString = phpString + workerId + "&trialNum=" + trialNum + "&rewardValue=" + points;
                phpString = phpString + "&assignment=" + order + "&choice=1&RT=";
                phpString = phpString + ((int) (responseTime.getTime() - instructionStart.getTime()));

                if (showInstructions) {
                    phpCall(phpString);
                }

                RootPanel.get().remove(horizontalPanel);

                blockParams.moveAny = false;
                currentValue = targetValue;

                new Timer() {
                    public void run() {
                        position[1]++;
                        trialHandler();
                    }
                }.schedule(pauseDur);
            }
        });
    }

    public void trialHandler() {
        timerLabel.setText("");
        timerLabel.removeStyleName("redColour");
        
        progress.setProgress(trialNum);

        if (trialNum < blockParams.nTrials) {
            if (position[1] == 0) {
                initTrial(blockParams.blockNum, blockParams.nCircles, blockParams.totalCircles, blockParams.alphabetOrder, blockParams.defaultExit, blockParams.nPM);

                instructStart = new Date();

                if (showInstructions) {
                    if (position[0]>26) {
                        points = conditions[trialNum];
                    }
                    
                    trialInstructions();
                } else {
                    position[1]++;
                    trialHandler();
                }
            } else {
                instructEnd = new Date();

                int readingTime = (int) (instructEnd.getTime() - instructStart.getTime());

                String phpString = "output_instruct.php?participantID=";

                phpString = phpString + workerId + "&blockNum=" + blockParams.blockNum + "&trialNum=" + trialNum + "&readingTime=" + readingTime;

                //phpCall(phpString);
                position[1] = 0;
                runTrial();
            }
        } else {
            position[0]++;
            sequenceHandler();
        }
    }

    public void initTrial(final int blockNum, int nCircles, int totalCircles, int alphabetOrder, int defaultExit, int nPM) {
        reminderFlag = -1;
        backupReminderFlag = -1;

        Label debugLabel = new Label("test");

        int xDim = Window.getClientWidth();
        int yDim = Window.getClientHeight();
        int minDim = 0;

        if (xDim <= yDim) {
            minDim = xDim;
        } else {
            minDim = yDim;
        }

        boxSize = (int) (minDim * 0.75);
        margin = (int) (boxSize * 0.2);
        circleRadius = (int) (boxSize * 0.08);
        //clockText.setFontSize((int) boxSize * 0.035);

        String[] labels = new String[totalCircles];
        int[] targets = new int[totalCircles];

        for (int c = 0; c < totalCircles; c++) {
            targets[c] = 0;

            switch (alphabetOrder) {
                case FORWARD:
                    labels[c] = "" + (c + 1);
                    //labels[c] = String.valueOf((char) (65 + c));
                    break;
                case REVERSE:
                    labels[c] = String.valueOf((char) (74 - c));
                    break;
            }
        }

        //set PM exit points and set instructions
        String instructions = null;

        if (alphabetOrder == FORWARD) {
            instructions = "Please drag the letters in order to the bottom of the box<br>"
                    + "(A, B, C, D, etc.)<br><br>";
        }

        if (alphabetOrder == REVERSE) {
            instructions = "Please go in REVERSE through the alphabet, starting with J<br><br>";
        }

        //int totalTargets = (blockParams.totalCircles - 6) / 2;
        int totalTargets = blockParams.totalTargets;

        //set up target directions
        List<Integer> targetDirections = new ArrayList<Integer>();

        int targetCount = 0;

        while (targetCount < totalTargets) {
            targetCount += 3;

            targetDirections.add(1);
            targetDirections.add(2);
            targetDirections.add(3);

        }

        //shuffle targetDirections
        for (int i = 0; i < targetDirections.size(); i++) {
            Collections.swap(targetDirections, i, Random.nextInt(targetDirections.size()));
        }

        /****************************************/
        /* start of code for distributing items */
        /****************************************/

        //set up target positions
        List<Integer> possibleTargetPositions = new ArrayList<Integer>();

        for (int i = 6; i < blockParams.totalCircles; i++) {
            possibleTargetPositions.add(i);
        }
        
        int binSize = possibleTargetPositions.size() / totalTargets;
        int remainingItems = possibleTargetPositions.size() % totalTargets;
        
        List<Integer> binSizes = new ArrayList<Integer>();
        
        for (int i = 0; i < remainingItems; i++) {
            binSizes.add(binSize+1); //add a bin of minimum size + 1 for each of the remaining items
        }
        
        for (int i = 0; i < totalTargets - remainingItems; i++) {
            binSizes.add(binSize); // now add the standard bin size for the other items
        }
        
        //now shuffle the binSizes
        for (int i = 0; i < binSizes.size(); i++) {
            Collections.swap(binSizes, i, Random.nextInt(binSizes.size()));
        }

        //put actual target positions in this variable
        List<Integer> targetPositions = new ArrayList<Integer>();
        
        //set up binpositions variable, collecting all positions within a single bin
        List<Integer> binPositions = new ArrayList<Integer>();
        
        //now loop over the targets and pick middle of corresponding bin
        for (int i = 0; i < totalTargets; i++) {
            for (int ii = 0; ii < binSizes.get(i); ii++) {
                binPositions.add(possibleTargetPositions.get(0));
                possibleTargetPositions.remove(0);
            }
            
            //get middle item from binPositions
            int middle = binPositions.size() / 2;

            if ((binPositions.size() % 2) == 0) { //if it's even randomly subtract 1 half the time
                middle -= Random.nextInt(2);         
            }

            targetPositions.add(binPositions.get(middle));   
            
            //now empty binPositions variable
            binPositions.clear();
        }
        
        //set up targets variable
        for (int i = 0; i < totalTargets; i++) {
            targets[targetPositions.get(0)] = targetDirections.get(0);

            targetPositions.remove(0);
            targetDirections.remove(0);
        }

        trialParams.blockNum = blockNum;
        trialParams.nCircles = nCircles;
        trialParams.totalCircles = totalCircles;
        trialParams.labels = labels;
        trialParams.instructions = instructions;
        trialParams.cuedTargets = blockParams.cuedTargets;
        trialParams.showClock = blockParams.showClock;
        trialParams.targets = targets;
    }

    public void runTrial() {
        blockStart = new Date();

        final LienzoPanel panel = new LienzoPanel(boxSize, boxSize);

        //Window.setMargin("0px");
        final VerticalPanel verticalPanel = new VerticalPanel();

        verticalPanel.setWidth(Window.getClientWidth() + "px");
        verticalPanel.setHeight(Window.getClientHeight() + "px");

        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        final HorizontalPanel horizontalPanel = new HorizontalPanel();

        horizontalPanel.add(panel);

        verticalPanel.add(horizontalPanel);
        RootPanel.get().add(verticalPanel);

        //set up outline
        Layer bgLayer = new Layer();

        Line left = new Line(0, 0, 0, boxSize).setStrokeColor(circleColours[1]).setStrokeWidth(10);
        Line right = new Line(boxSize, 0, boxSize, boxSize).setStrokeColor(circleColours[2]).setStrokeWidth(10);
        Line bottom = new Line(0, boxSize, boxSize, boxSize).setStrokeColor(ColorName.BLACK).setStrokeWidth(10);
        Line top = new Line(0, 0, boxSize, 0).setStrokeColor(circleColours[3]).setStrokeWidth(10);

        //Rectangle outlineRectangle = new Rectangle(boxSize, boxSize);
        //outlineRectangle.setStrokeColor(ColorName.BLACK).setStrokeWidth(6);
        //bgLayer.add(outlineRectangle);
        bgLayer.add(left);
        bgLayer.add(right);
        bgLayer.add(bottom);
        bgLayer.add(top);
        //put background layer on screen
        panel.add(bgLayer);

        //set up circles
        final Layer circleLayer = new Layer();

        final Circle targetHitCircle = new Circle(circleRadius);
        targetHitCircle.setFillColor(ColorName.WHITE);
        targetHitCircle.setStrokeColor(ColorName.GRAY);
        targetHitCircle.setStrokeWidth(2);

        final Text targetText = new Text("+" + targetValue, "Verdana, sans-serif", null, 40);
        targetText.setTextAlign(TextAlign.CENTER);
        targetText.setTextBaseLine(TextBaseLine.MIDDLE);
        targetText.setFillColor(ColorName.GRAY);

        final Group targetHit = new Group();
        targetHit.add(targetHitCircle);
        targetHit.add(targetText);

        targetHit.setVisible(false);
        targetHit.setX((int) boxSize / 2);
        targetHit.setY((int) boxSize / 2);
        targetHit.setAlpha(0.3);

        int[] circleX = new int[trialParams.nCircles];
        int[] circleY = new int[trialParams.nCircles];
        final Circle[] circles = new Circle[trialParams.nCircles];
        final Circle[] circleOverlays = new Circle[trialParams.nCircles];
        final Text[] circleText = new Text[trialParams.nCircles];
        final Group[] circleGroup = new Group[trialParams.nCircles];
        int randomFlag = 0;
        completedCircles = 0;
        nextCircle = 0;
        nTargets = 0;
        nHits = 0;
        circleAdjust = 0;

        int c = 0;
        int randomCounter = 0;

        while (c < trialParams.nCircles) {
            randomFlag = 1;

            while (randomFlag == 1) {
                randomCounter++;

                if (randomCounter > 10000) {
                    randomCounter = 0;
                    c = 0; // start again if failing to position the circles appropriately
                }

                randomFlag = 0;

                circleX[c] = Random.nextInt(boxSize - (2 * circleRadius) - (2 * margin)) + circleRadius + margin;
                circleY[c] = Random.nextInt(boxSize - (2 * circleRadius) - (2 * margin)) + circleRadius + margin;

                for (int cc = 0; cc < c; cc++) {
                    int distanceX = circleX[c] - circleX[cc];
                    int distanceY = circleY[c] - circleY[cc];

                    double distanceAbs = Math.pow(Math.pow(distanceX, 2) + Math.pow(distanceY, 2), 0.5);

                    if (distanceAbs < (3 * circleRadius)) {
                        randomFlag = 1;
                    }
                }
            }

            c++;
        }

        final int[] circleXref = circleX;
        final int[] circleYref = circleY;

        for (c = 0; c < trialParams.nCircles; c++) {
            circles[c] = new Circle(circleRadius);
            circleOverlays[c] = new Circle(circleRadius);
            circleText[c] = new Text(trialParams.labels[c], "Verdana, sans-serif", null, 40);
            circleGroup[c] = new Group();

            circles[c].setFillColor(circleColours[0]);
            circles[c].setStrokeColor(ColorName.BLACK);
            circles[c].setStrokeWidth(2);

            circleText[c].setTextAlign(TextAlign.CENTER);
            circleText[c].setTextBaseLine(TextBaseLine.MIDDLE);
            circleText[c].setFillColor(ColorName.BLACK);
            
            circleOverlays[c].setFillColor(ColorName.BLACK);
            circleOverlays[c].setAlpha(0.0000001);

            circleGroup[c].add(circles[c]);
            circleGroup[c].add(circleText[c]);
            circleGroup[c].add(circleOverlays[c]);
            circleGroup[c].setX(circleX[c]);
            circleGroup[c].setY(circleY[c]);

            if (blockParams.moveAny == true) {
                circleGroup[c].setDraggable(true);
            } else {
                circleGroup[c].setDraggable(false);
            }

            circleGroup[c].setDragBounds(new DragBounds(1, 1, boxSize, boxSize));

            circleLayer.add(circleGroup[c]);

            circleGroup[c].addNodeDragStartHandler(new NodeDragStartHandler() {
				@Override
				public void onNodeDragStart(NodeDragStartEvent event) {
					quitFlag = false;

                    dragStart = new Date();

                    DragContext dC = event.getDragContext();

                    //figure out which circle is clicked
                    for (int c = 0; c < trialParams.nCircles; c++) {
                        double xDist = dC.getDragStartX() - circleGroup[c].getX();
                        double yDist = dC.getDragStartY() - circleGroup[c].getY();

                        if (Math.pow(xDist, 2) <= Math.pow(circleRadius, 2)) {
                            if (Math.pow(yDist, 2) <= Math.pow(circleRadius, 2)) {
                                clickedCircle = c;
                            }
                        }
                    }   
				}
            });

            circleLayer.add(targetHit);

            circleGroup[c].addNodeDragMoveHandler(new NodeDragMoveHandler() {
				@Override
				public void onNodeDragMove(NodeDragMoveEvent event) {
					if (clickedCircle == reminderFlag) {
                        reminderFlag = -1;

                        if (reminderFlag == backupReminderFlag) {
                            backupReminderFlag = -1;
                        }
                    }

                    if (clickedCircle == backupReminderFlag) {
                        backupReminderFlag = -1;
                    }

                    if (event.getX() <= 0) { //left
                        exitFlag = 1;
                    }

                    if (event.getX() >= boxSize) { //right
                        exitFlag = 2;
                    }

                    if (event.getY() <= 0) { //up
                        exitFlag = 3;
                    }

                    if (event.getY() >= boxSize) { //down
                        exitFlag = 4;
                    }

                    if (exitFlag > 0) {
                        DragContext dC = event.getDragContext();

                        if ((clickedCircle == nextCircle) && ((reminderFlag == -1) || ((completedCircles - reminderCompletedCircles) < 1))) {
                            if (checkExit == 1) {
                                reminderFlag = backupReminderFlag;
                                reminderCompletedCircles = backupCompletedCircles;

                                backupReminderFlag = -1;

                                checkExit = 0;

                                if (exitFlag == trialParams.targets[clickedCircle + circleAdjust]) {
                                    nHits++;
                                    circles[clickedCircle].setFillColor(ColorName.GREENYELLOW);
                                } else if (exitFlag < 4) { //incorrect target response     
                                    circles[clickedCircle].setFillColor(ColorName.RED);
                                } else { //ongoing response
                                    //circles[clickedCircle].setFillColor(ColorName.PURPLE);
                                }

                                circleText[clickedCircle].setVisible(false);
                                //circleGroup[clickedCircle].setDragBounds(new DragBounds(dC.getEventX()-10, dC.getEventY()-10, dC.getEventX()+10, dC.getEventY()+10));
                                circleLayer.draw();

                            }
                        } else {
                            if (flashFlag == false) {
                                if (clickedCircle != nextCircle) {
                                    circles[nextCircle].setFillColor(ColorName.RED);
                                } else {
                                    //Window.alert("reminderFlag = " + reminderFlag + "&backupReminderFlag = " + backupReminderFlag);
                                    circles[reminderFlag].setFillColor(ColorName.WHITE);
                                }

                                circleLayer.draw();

                                new Timer() {
                                    public void run() {
                                        circles[nextCircle].setFillColor(circleColours[0]);
                                        circles[reminderFlag].setFillColor(circleColours[0]);
                                        circleLayer.draw();
                                    }
                                }.schedule(400);

                                flashFlag = true;
                            }

                            exitFlag = 0;
                        }
                    }
				}
            });

            circleGroup[c].addNodeDragEndHandler((NodeDragEndHandler) new NodeDragEndHandler() {
				@Override
				public void onNodeDragEnd(NodeDragEndEvent event) {
					AnimationProperties grow = new AnimationProperties();
                    grow.push(Properties.SCALE(5));

                    if (exitFlag == trialParams.targets[clickedCircle + circleAdjust]) {
                        if ((exitFlag > 0) && (showInstructions)) {
                            totalPoints += currentValue;
                        }

                        if ((exitFlag > 0) && showPoints) {
                            targetText.setText("+" + currentValue);
                            targetHit.setVisible(true);
                            IAnimationHandle handleFeedback = targetHit.animate(AnimationTweener.LINEAR, grow, 200);
                        }
                    }

                    int extraAdjust = 0;

                    if (clickedCircle < nextCircle) {
                        extraAdjust = trialParams.nCircles;
                    }

                    if ((exitFlag == 0) && (trialParams.targets[clickedCircle + circleAdjust + extraAdjust] > 0)) {
                        if (showPoints) {
                            targetText.setText("-5");
                            targetHit.setVisible(true);
                            IAnimationHandle handleFeedback = targetHit.animate(AnimationTweener.LINEAR, grow, 200);
                        }
                    }

                    final AnimationProperties shrink = new AnimationProperties();
                    shrink.push(Properties.SCALE(0));

                    new Timer() {
                        public void run() {
                            IAnimationHandle handleFeedback2 = targetHit.animate(AnimationTweener.LINEAR, shrink, 200);
                        }
                    }.schedule(350);

                    dragEnd = new Date();

                    //outputData(trialParams.nCircles, circleGroup, trialParams.labels, trialParams.blockNum);     
                    flashFlag = false;
                    
                    if ((exitFlag == 0) & (clickedCircle != nextCircle)) {
                        for (int c = 0; c < trialParams.nCircles; c++) {
                            circleGroup[c].setDraggable(false);
                            circles[c].setAlpha(0.3);
                        }
                        
                        circleLayer.draw();
                        
                        new Timer() {
                            public void run() {                               
                                for (int c = 0; c < trialParams.nCircles; c++) {
                                    circleGroup[c].setDraggable(true);
                                    circles[c].setAlpha(1);
                                }
                                
                                circleLayer.draw();
                            }
                        }.schedule(lockOutTime);     
                    }

                    if ((exitFlag > 0) && (clickedCircle == nextCircle)) {
                        completedCircles++;

                        if (trialParams.targets[completedCircles + 5] > 0) {
                            nTargets++;
                        }

                        if ((completedCircles % trialParams.nCircles) == 0) {
                            nextCircle -= trialParams.nCircles;
                            circleAdjust += trialParams.nCircles;
                        }

                        IAnimationHandle handle = circles[clickedCircle].animate(AnimationTweener.LINEAR, shrink, 200);

                        if ((trialParams.totalCircles - completedCircles) >= trialParams.nCircles) { //more circles to add on screen
                            final int clickedCircleFinal = clickedCircle;
                            final int completedCirclesFinal = completedCircles;

                            if (trialParams.targets[completedCirclesFinal + 5] > 0) {
                                if ((noReminder == 0) && (blockParams.moveAny)) {
                                    //if ((currentValue==targetValue)&&(blockParams.moveAny)) {
                                    //Window.alert("backup");
                                    backupReminderFlag = clickedCircleFinal;
                                    backupCompletedCircles = completedCirclesFinal;

                                    if ((completedCirclesFinal - reminderCompletedCircles) > 1) {
                                        reminderFlag = clickedCircleFinal;
                                        reminderCompletedCircles = completedCirclesFinal;
                                    }
                                }
                            }

                            new Timer() {
                                public void run() {
                                    circles[clickedCircleFinal].setFillColor(circleColours[trialParams.targets[completedCirclesFinal + 5]]);
                                    circleGroup[clickedCircleFinal].setX(circleXref[clickedCircleFinal]);
                                    circleGroup[clickedCircleFinal].setY(circleYref[clickedCircleFinal]);

                                    if (blockParams.moveAny == false) {
                                        circleGroup[clickedCircleFinal].setDraggable(false);
                                    }

                                    AnimationProperties grow = new AnimationProperties();
                                    grow.push(Properties.SCALE(1));
                                    

                                    IAnimationHandle handle = circles[clickedCircleFinal].animate(AnimationTweener.LINEAR, grow, 200);
                                }
                            }.schedule(300);

                            new Timer() {
                                public void run() {
                                    circleText[clickedCircleFinal].setText(trialParams.labels[completedCirclesFinal + trialParams.nCircles - 1]);

                                    circleText[clickedCircleFinal].setVisible(true);


                                    circleLayer.draw();
                                }
                            }.schedule(400);

                            final double startR = (double) circleColours[trialParams.targets[completedCirclesFinal + 5]].getR();
                            final double startG = (double) circleColours[trialParams.targets[completedCirclesFinal + 5]].getG();
                            final double startB = (double) circleColours[trialParams.targets[completedCirclesFinal + 5]].getB();

                            final double endR = (double) circleColours[0].getR();
                            final double endG = (double) circleColours[0].getG();
                            final double endB = (double) circleColours[0].getB();

                            new Timer() {
                            	public void run() {
                            		IAnimationCallback callback = new IAnimationCallback() {
                            			public void onClose(IAnimation callback, IAnimationHandle handle) {		
                            			
                            			}
                            			
                            			public void onFrame(IAnimation callback, IAnimationHandle handle) {	
                            				double percent = callback.getPercent();
                            				double newR = startR + (percent * (endR - startR));
                                            double newG = startG + (percent * (endG - startG));
                                            double newB = startB + (percent * (endB - startB));

                                            int R = (int) newR;
                                            int G = (int) newG;
                                            int B = (int) newB;

                                            Color newColor = new Color(R, G, B);

                                            circles[clickedCircleFinal].setFillColor(newColor);
                                            circleLayer.draw();
                            			}
                            			
                            			public void onStart(IAnimation callback, IAnimationHandle handle) {		
                                			
                            			}
                            		};
                            		
                            		IAnimationHandle handle = circles[clickedCircleFinal].animate(null, null, 400, callback);
                            	}
                            }.schedule(2000);
                            
                           
                            
                            /*
                            new Timer() {
                                public void run() {
                                    IAnimationCallback callback = new IAnimationCallback() {
										public void onStart(IAnimation animation, IAnimationHandle handle) {
											// TODO Auto-generated method stub
											
										}


	
										public void onClose(IAnimation animation, IAnimationHandle handle) {
											circles[clickedCircleFinal].setFillColor(circleColours[0]);
                                            circleLayer.draw();  
										}

										public void onFrame(IAnimation animation, IAnimationHandle handle, long duration, double percent) {
											double newR = startR + (percent * (endR - startR));
                                            double newG = startG + (percent * (endG - startG));
                                            double newB = startB + (percent * (endB - startB));

                                            int R = (int) newR;
                                            int G = (int) newG;
                                            int B = (int) newB;

                                            Color newColor = new Color(R, G, B);

                                            circles[clickedCircleFinal].setFillColor(newColor);
                                            circleLayer.draw();
										



										@Override
										public void onFrame(IAnimation animation, IAnimationHandle handle) {
											// TODO Auto-generated method stub
											
										}
                                    };

                                    IAnimationHandle handle = circles[clickedCircleFinal].animate(null, shrink, 400, callback);
                                }
                            }.schedule(2000);*/
                        }

                        checkExit = 1; // ready for next exit event
                        exitFlag = 0;
                        nextCircle++;

                        if (completedCircles == trialParams.totalCircles) {
                            new Timer() {
                                public void run() {
                                    final Date endTime = new Date();

                                    String phpString = "postTrial.php?participantID=";

                                    if (position[0]>22) {
                                        phpString = phpString + workerId + "&trialNum=" + trialNum + "&rewardValue=" + points;
                                        phpString = phpString + "&nHits=" + nHits + "&noReminder=" + noReminder;
                                        phpString = phpString + "&duration=" + ((int) (endTime.getTime() - blockStart.getTime()));
                                    } else {
                                        phpString = phpString + workerId + "&trialNum=" + (trialNum-100) + "&rewardValue=" + points;
                                        phpString = phpString + "&nHits=" + nHits + "&noReminder=" + noReminder;
                                        phpString = phpString + "&duration=" + ((int) (endTime.getTime() - blockStart.getTime()));
                                    }
                                    
                                    phpCall(phpString);
                                    
                                    timeCounter = 1;
                                    
                                    final String phpStringFinal=phpString;

                                    new Timer() {
                                        public void run() {
                                            timeCounter++;
                                            
                                            if ((phpOutput.equals("OK"))|checkSaved==false) {
                                                cancel();
                                                
                                                trialTimer.cancel();
                                                
                                                RootPanel.get().remove(verticalPanel);

                                                trialNum++;

                                                new Timer() {
                                                    public void run() {
                                                        for (int c = 0; c < trialParams.nCircles; c++) {
                                                            circles[c] = null;
                                                            circleGroup[c] = null;
                                                            circleText[c] = null;
                                                        }

                                                        if (showFeedback) {
                                                            giveFeedback();
                                                        } else {
                                                            trialHandler();
                                                        }
                                                    }
                                                }.schedule(1000);
                                            }
                                            
                                            if (timeCounter > 70) {
                                                Window.alert("Data was not saved. Please check your internet connection before continuing");
                                                timeCounter=1;
                                                phpCall(phpStringFinal);
                                            }
                                        }
                                    }.scheduleRepeating(100);
                                }
                            }.schedule(1000);
                        } else {
                            if (nextCircle < trialParams.nCircles) {
                                circleGroup[nextCircle].setDraggable(true);
                            }
                        }
                    }
				}
            });
        }

        circleGroup[0].addNodeMouseDoubleClickHandler((NodeMouseDoubleClickHandler) new NodeMouseDoubleClickHandler() {
			@Override
			public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent event) {
				quitFlag = true;
			}
        }
        );

        circleGroup[trialParams.nCircles - 1].addNodeMouseDoubleClickHandler(new NodeMouseDoubleClickHandler() {
            public void onNodeMouseDoubleClick(NodeMouseDoubleClickEvent event) {
                if (quitFlag == true) {
                    RootPanel.get().remove(verticalPanel);
                    trialNum++;
                    trialHandler();
                    return;
                }
            }
        });

        circleGroup[0].setDraggable(true);

        panel.add(circleLayer);
        circleLayer.draw();
        
        trialTimeCounter=180;
        trialTimer.scheduleRepeating(1000);
    }
    
    private void giveFeedback() {
        String msg="";
        /*
        if (feedbackGroup == POSITIVE) {
            if (nHits==0) {
                msg = "You did not get any special circles correct this time.";
            } else if (nHits==1) {
                msg = "Well done - good work! You got 1 special circle correct this time.";
            } else {
                msg = "Well done - good work! You got " + nHits + " special circles correct this time.";
            }
        } else if (feedbackGroup == NEGATIVE) {
            if (nHits==blockParams.totalTargets) {
                msg = "You did not get any special circles wrong this time.";
            } else if (nHits==blockParams.totalTargets-1) {
                msg = "Room for improvement. You got 1 special circle wrong this time.";
            } else {
                msg = "Room for improvement. You got " + nHits + " special circles correct this time.";
            }   
        }
        */
        
        if (feedbackGroup == POSITIVE) {
            if (nHits==0) {
                msg = "You did not get any special circles correct this time.";
            } else if (nHits <= (blockParams.totalTargets/2)) {
                msg = "Well done - good work! You are responding well to the special circles.";
            } else if (nHits == blockParams.totalTargets) {
                msg = "Well done - perfect! You responded correctly to all of the special circles.";
            } else {
                msg = "Well done - excellent work! You responded correctly to most of the special circles.";
            }
        } else if (feedbackGroup == NEGATIVE) {
            if (nHits==blockParams.totalTargets) {
                msg = "You did not get any special circles wrong this time.";
            } else if (nHits < (blockParams.totalTargets/2)) {
                msg = "Room for improvement. You got most of the special circles wrong.";
            } else if (nHits == 0) {
                msg = "Room for improvement. You got all of the special circles wrong.";
            } else {
                msg = "Room for improvement. You got some of the special circles wrong.";
            }   
        }
        
        
        centreHtmlClick(msg, "Next", 2);
    }

    private double calculateAverage(List<Integer> integerList) {
        Integer sum = 0;
        if (!integerList.isEmpty()) {
            for (Integer value : integerList) {
                sum += value;
            }
            return sum.doubleValue() / integerList.size();
        }
        return sum;
    }

    public void centreHtmlPress(String... args) {
        String htmlText = args[0];

        final HTML displayText = new HTML(htmlText);
        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final VerticalPanel verticalPanel = new VerticalPanel();
        final FocusPanel focusPanel = new FocusPanel();

        //set up vertical panel
        verticalPanel.setWidth("75%");
        verticalPanel.setHeight(Window.getClientHeight() + "px");

        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        //add text to panel
        displayText.setStyleName("instructionText");
        verticalPanel.add(displayText);

        //place vertical panel inside horizontal panel, so it can be centred
        horizontalPanel.setWidth(Window.getClientWidth() + "px");
        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        horizontalPanel.add(verticalPanel);

        //place horizontal panel inside focus panel, so it can receive text input
        focusPanel.add(horizontalPanel);

        //add panel to root
        RootPanel.get().add(focusPanel);
        focusPanel.setFocus(true);

        //set up clickhandler  
        focusPanel.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
                RootPanel.get().remove(focusPanel);

                new Timer() {
                    public void run() {
                        position[0]++;
                        sequenceHandler();
                    }
                }.schedule(pauseDur);
            }
        });
    }

    public void centreHtmlClick(String htmlText, String buttonText, final int positionLoop) {
        final HTML displayText = new HTML(htmlText);
        final Button continueButton = new Button(buttonText);
        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final VerticalPanel verticalPanel = new VerticalPanel();

        //set up vertical panel
        verticalPanel.setWidth("75%");
        //verticalPanel.setHeight(Window.getClientHeight() + "px");
        verticalPanel.setHeight("75%");

        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        //add elements to panel
        displayText.setStyleName("instructionText");
        verticalPanel.add(displayText);
        verticalPanel.add(continueButton);

        //place vertical panel inside horizontal panel, so it can be centred
        horizontalPanel.setWidth(Window.getClientWidth() + "px");
        horizontalPanel.setHeight(Window.getClientHeight() + "px");

        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        horizontalPanel.add(verticalPanel);

        //add panel to root
        RootPanel.get().add(horizontalPanel);

        //set up clickhandler  
        continueButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(horizontalPanel);

                new Timer() {
                    public void run() {
                        if (positionLoop == 0) {
                            position[0]++;
                            sequenceHandler();
                        } else if (positionLoop == 1) {
                            position[1]++;
                            trialHandler();
                        } else if (positionLoop == 2) {
                            trialHandler();
                        }
                    }
                }.schedule(pauseDur);
            }
        });
    }

    public void finish() {
        RootPanel.get().clear();
        
        final HTML goodbyeText = new HTML("Thank you for taking part.<br><br>If you would like "
                + "to contact the experimenter you can email "
                + "<a href=\"mailto:sam.gilbert@ucl.ac.uk\">sam.gilbert@ucl.ac.uk</a>."
                + "<br><br>To receive your payment "
                + "please copy the survey code below into the Mechanical Turk website:"
                + "<br><br>" + rewardCode);

        final HTML commentText = new HTML("<br><br>Do you have any concerns about "
                + "this experiment, or is there anything else you would like to tell "
                + "the experimenter?");

        final TextArea commentTextArea = new TextArea();

        commentTextArea.setCharacterWidth(128);
        commentTextArea.setVisibleLines(4);

        final Button button = new Button("Submit");

        final VerticalPanel commentPanel = new VerticalPanel();

        commentPanel.add(commentText);
        commentPanel.add(commentTextArea);
        commentPanel.add(button);

        RootPanel.get().add(goodbyeText);
        RootPanel.get().add(commentPanel);

        String phpString = "finished.php?participantID=";
        phpString = phpString + workerId + "&counterbalancing=" + counterbalancing + "&difficultyGroup=" + difficultyGroup;
        phpString = phpString + "&feedbackGroup=" + feedbackGroup + "&points=" + totalPoints;
        phpString = phpString + "&slider=" + sliderValue;
        
        phpCall(phpString);
        
        finishParticipantID();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(commentPanel);

                String phpString = "comment.php?participantID=";
                phpString = phpString + workerId + "&comment=" + commentTextArea.getText();

                phpCall(phpString);
            }
        });
    }

    public String instruct(int pagenum) {
        String text = "";

        switch (pagenum) {
            case 1:
                text = "In this experiment you will have a simple task to do.<br><br>"
                        + "You will see several yellow circles inside a box. "
                        + "Inside each circle will be a number. <br><br>"
                        + "You can move them around using your mouse. Your task is to drag them to the bottom "
                        + "of the box in sequence. "
                        + "Please start by dragging 1 all the way to the bottom. "
                        + "This will make it disappear. Then drag 2 to the bottom, then 3, "
                        + "and so on.<br><br>";
                break;
            case 2:
                text = "Now you will continue the same task, but sometimes there will be something else to "
                        + "do.<br><br>As well as dragging each circle in turn to the "
                        + "bottom of the screen, there will sometimes be special "
                        + "circles that you should drag in another direction (left, top, or right) instead of towards the bottom.<br><br>"
                        + "These special circles will initially appear in a different colour "
                        + "when they are first shown on the screen, instead of yellow. This is an "
                        + "instruction telling you where they should go.<br><br>"
                        + "For example, suppose that the circle with 7 in it was first shown in blue "
                        + "when it appeared on the screen. That would be an instruction that "
                        + "when you get to 7 in the sequence, you should drag that circle "
                        + "to the blue side of the box (left) instead of the bottom.<br><br>"
                        + "You will still be able to drag any "
                        + "circle to the bottom of the box, but you should try to "
                        + "remember to drag these special circles to the instructed "
                        + "location instead.";
                break;
            case 3:
                text = "You didn't drag the special circle to the correct location.<br><br>Please try again.";
                break;
            case 4:
                text = "Well done, that was correct.<br><br>Now it will get more difficult. "
                        + "There will be a total of 25 circles, and " + pracTargets + " of them will be special ones "
                        + "that should go to one of the coloured sides of the box.<br><br>Don't worry if you "
                        + "do not remember all of them. That's fine - just try to remember as many as you can.";
                break;
            case 41:
                String d = "";
                if (difficultyGroup==EASY) {
                    d="difficult";
                } else {
                    d="easy";
                }
                
                text = "Now the task will get more " + d + ". It will stay like this for the rest of the experiment.<br><br>"
                        + "Please ignore the difficulty of the practice trials you have just done and remember that the task "
                        + "will be like this from now on.<br><br>Click below to continue";
                break;
            case 42:
                text = "Now that you have had some practice with the experiment, we would like you to tell us "
                        + "how accurately you can perform the task. Please ignore the earlier practice trials and "
                        + "just tell us how accurately you can do the task when it is the same difficulty as the trial you "
                        + "have just completed. The difficulty will stay the same as this for the rest of the experiment.<br><br>"
                        + "Please use the scale below to indicate what percentage of "
                        + "the special circles you can correctly drag to the instructed side of the square, on average. 100% "
                        + "would mean that you always get every single one correct. 0% would mean that you can never "
                        + "get any of them correct.";
                break;
            case 5:
                text = "Now we are going to explain a strategy that can make the task easier.<br><br>"
                        + "When you see a special circle, you can set a reminder by immediately dragging it to a "
                        + "different part of the box. For example, if a circle initially appeared in blue, you "
                        + "could immediately drag it next to the blue (left) side of the box. Then, when "
                        + "you get to that circle in the sequence its location would remind you where it is supposed "
                        + "to go.<br><br>Please now try the task again, using this strategy to help you.";
                break;
            case 6:
                text = "You only got " + nHits + " out of 10 correct. You need to get at least 8 out of 10 correct "
                        + "to continue to the next part.<br><br>Please keep in mind that you can set reminders to "
                        + "help you remember. Each time a special circle appears, you can immediately "
                        + "drag it next to the part of the box where it eventually needs to go so. This should help "
                        + "remind you what to do when you get to that circle in the sequence.";
                break;
            case 7:
                text = "From now on, you will score points every time you drag one of the special circles "
                        + "to the correct location.<br><br>You should try to score as many points as you can.";
                break;
            case 77:
                text = "Now the experiment will start for real. From now on, you will score points "
                        + "every time you drag one of the special circles to the correct location.<br><br>You "
                        + "should aim to score as many points as you can. "
                        + "<br><br>These points are worth real money, which you will receive at the end of the task. "
                        + "For every 100 points you score, you will receive £0.30. "
                        + "Over the course of the experiment, "
                        + "this means you can earn a bonus of over £5.00. You will earn this on top of a base payment of £5.00, so your "
                        + "total payment for doing the experiment can be over £10.00."
                        + " So please try to score as many points as possible, so that you earn "
                        + "as much money as you can.";
                break;
            case 8:
                text = "Sometimes when you do the task, you will have to do it without setting any reminders.<br><br>"
                        + "When this happens, you will score <b>" + targetValue + "</b> points for every special circle you remember.<br><br>"
                        + "You will always be given clear instructions what you should do. In this case you will be "
                        + "told \"This time you must do the task without setting any reminders\" and see a red button. "
                        + "When this happens, "
                        + "the computer will not let you set any reminders.<br><br>Let's practise that now.";
                break;
            case 9:
                text = "Other times, you will have to set reminders for all the special circles.<br><br>When "
                        + "this happens, you will also score <b>" + targetValue + "</b> points for every special circle you remember.<br><br>"
                        + "In this case, you will be told \"This time you <b>must</b> set a reminder for every special circle\" "
                        + "and you will see a green button.<br><br>"
                        + "When this happens, the computer will make sure that you always set a reminder for every "
                        + "circle and it will not let you continue if you do not.<br><br>Let's practice that now.";
                break;
            case 10:
                text = "Sometimes, you will have a choice between two options when you do the task. One option will be to do the task "
                        + "without being able to set any reminders. If you choose this option, you will always score "
                        + targetValue + " points for every special circle you remember.<br><br>The other option will be to "
                        + "do the task with reminders, but in this case each special circle will be worth "
                        + "fewer points. For example, you might be told that if you want to use reminders, "
                        + "each special circle will be worth only 5 points.<br><br>You should choose whichever "
                        + "option you think will score you the most points. <br><br>So if, for example, you "
                        + "thought you would earn more points by setting reminders and scoring 5 points for "
                        + "each special circle, you should choose this option. But if you thought you would "
                        + "score more points by just using your own memory and earning " + targetValue + " points for each special "
                        + "circle, you should choose this option instead.";
                break;
            case 11:
                text = "When you are presented with a choice like this, it is completely up to you. "
                        + "You should do whatever you think will allow you to score the highest number of points.<br><br>"
                        + "You are now in the final part of the experiment. When the progress bar above is complete the experiment will "
                        + "end.";
                break;
            case 12:
                text = "Now that you have had some practice with the experiment, we would like you to tell us "
                        + "how accurately you can perform the task when you do it <b>without</b> using any "
                        + "reminders.<br><br>Please use the scale below to indicate what percentage of "
                        + "the special circles you can correctly drag to the instructed side of the square, on average. 100% "
                        + "would mean that you always get every single one correct. 0% would mean that you can never "
                        + "get any of them correct.";
                break;

            case 13:
                text = "Now please tell us how accurately you can perform the task <b>with</b> reminders. "
                        + "As before, 100% would mean that you always get every special circle correct. 0% would mean "
                        + "that you can never get any of them correct.";
                break;
        }

        return (text);

    }

    private void getSliderValue(String instruct) {
        final Button continueButton = new Button("Next");
        final int sliderWidth = 400;
        final int sliderHeight = 20;
        final int width = sliderWidth + 2 * sliderHeight;
        final int height = 5 * sliderHeight;

        final int sliderRange = sliderWidth - (2 * sliderHeight);

        LinearGradient gradient = new LinearGradient(0, -50, 0, 50);
        gradient.addColorStop(0.5, "#4DA4F3");
        gradient.addColorStop(0.8, "#ADD9FF");
        gradient.addColorStop(1, "#9ED1FF");

        LienzoPanel lienzoPanel = new LienzoPanel(width, height);

        Rectangle slider = new Rectangle(sliderWidth, sliderHeight, sliderHeight / 2);

        slider.setX(sliderHeight).setY(2 * sliderHeight).setFillGradient(gradient).setStrokeColor(ColorName.GRAY.getValue()).setStrokeWidth(1);

        final Circle thumbCircle = new Circle(2 * sliderHeight);
        final Text thumbText = new Text("50%", "Verdana, sans-serif", null, 12);
        final Group thumb = new Group();

        thumbText.setTextAlign(TextAlign.CENTER);
        thumbText.setTextBaseLine(TextBaseLine.MIDDLE);
        thumbText.setFillColor(ColorName.BLACK);

        thumb.add(thumbCircle);
        thumb.add(thumbText);

        thumb.setX(slider.getX() + sliderHeight + (sliderRange / 2));
        thumb.setY(slider.getY() + sliderHeight / 2);
        thumbCircle.setStrokeColor(ColorName.GRAY.getValue());
        thumbCircle.setStrokeWidth(1);
        thumb.setDraggable(true);
        thumb.setDragConstraint(DragConstraint.HORIZONTAL);
        thumb.setDragBounds(new DragBounds().setX1(slider.getX() + sliderHeight).setX2(slider.getX() + sliderWidth - sliderHeight));
        thumbCircle.setFillColor(ColorName.LIGHTGRAY.getValue());

        thumb.addNodeDragStartHandler(new NodeDragStartHandler() {
            public void onNodeDragStart(NodeDragStartEvent event) {
                thumbCircle.setFillColor(ColorName.LIGHTPINK.getValue());
                thumbCircle.setShadow(new Shadow("gray", 5, 1, 1));
                thumb.getLayer().draw();
                continueButton.setEnabled(true);
            }
        });

        thumb.addNodeDragEndHandler(new NodeDragEndHandler() {
            public void onNodeDragEnd(NodeDragEndEvent event) {
                thumbCircle.setShadow(null);
                thumb.getLayer().draw();
            }
        });

        thumb.addNodeDragMoveHandler(new NodeDragMoveHandler() {
            @Override
            public void onNodeDragMove(NodeDragMoveEvent event) {
                DragContext dC = event.getDragContext();

                int offset = dC.getDragStartX() + dC.getDx();

                if (offset > sliderWidth) {
                    offset = sliderWidth;
                }

                if (offset < 2 * sliderHeight) {
                    offset = 2 * sliderHeight;
                }

                sliderValue = (int) ((100 * (offset - 2 * sliderHeight)) / sliderRange);
                thumbText.setText(sliderValue + "%");
            }
        });

        Layer canvas = new Layer();
        canvas.add(slider);
        canvas.add(thumb);

        lienzoPanel.add(canvas);

        final HorizontalPanel sliderPanel = new HorizontalPanel();

        final Label leftValue = new Label("0%");
        final Label rightValue = new Label("100%");

        sliderPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        sliderPanel.add(leftValue);
        sliderPanel.add(lienzoPanel);
        sliderPanel.add(rightValue);

        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final VerticalPanel verticalPanel = new VerticalPanel();

        //set up vertical panel
        verticalPanel.setWidth("75%");
        //verticalPanel.setHeight(Window.getClientHeight() + "px");
        verticalPanel.setHeight("75%");

        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        //add elements to panel
        final HTML displayText = new HTML(instruct);
        displayText.setStyleName("instructionText");
        verticalPanel.add(displayText);
        verticalPanel.add(sliderPanel);
        verticalPanel.add(continueButton);

        continueButton.setEnabled(false);

        //place vertical panel inside horizontal panel, so it can be centred
        horizontalPanel.setWidth(Window.getClientWidth() + "px");
        horizontalPanel.setHeight(Window.getClientHeight() + "px");

        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        horizontalPanel.add(verticalPanel);

        //add panel to root
        RootPanel.get().add(horizontalPanel);

        //set up clickhandler  
        continueButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(horizontalPanel);

                new Timer() {
                    public void run() {
                        position[0]++;
                        sequenceHandler();
                    }
                }.schedule(pauseDur);
            }
        });
    }

    public String ID_errorText() {
        return ("Your Mechanical Turk ID has already been used to complete this experiment.<br><br>"
                + "Although new HITs are occasionally posted for this task, it can only be completed once "
                + "by each worker. Apologies for this. If you think this is in error, please email <a href=mailto:"
                + "\"sam.gilbert@ucl.ac.uk\">sam.gilbert@ucl.ac.uk</a>.");
    }

    public String IP_errorText() {
        return ("You are using a computer or network that has already been used to access this "
                + "experiment. Unfortunately the experiment can only be accessed once from "
                + "any single network.");
    }

    public String infoText() {
        return ("We would like to invite you to participate in this research project. "
                + "You should only participate if you want to; choosing not to take part "
                + "will not disadvantage you in any way. Before you decide whether you "
                + "want to take part, please read the following information carefully and "
                + "discuss it with others if you wish. Ask us if there is anything that "
                + "is not clear or you would like more information.<br><br>"
                + "We are recruiting volunteers from the Amazon Mechanical Turk website to "
                + "take part in an experiment aiming to improve our understanding of human "
                + "attention and memory. Full instructions will be provided before the experiment begins. "
                + "The experiment "
                + "will last approximately 40 minutes. There are no anticipated risks or "
                + "benefits associated with participation in this study.<br><br>"
                + "It is up to you to decide whether or not to take part. If you choose "
                + "not to participate, you won't incur any penalties or lose any "
                + "benefits to which you might have been entitled. However, if you do "
                + "decide to take part, you can print out this information sheet and "
                + "you will be asked to fill out a consent form on the next page. "
                + "Even after agreeing to take "
                + "part, you can still withdraw at any time and without giving a reason. "
                + "<br><br>All data will be collected and stored in accordance with the UK Data "
                + "Protection Act 1998.");

    }

    public void informationSheet() {
        /*if (workerId.equals(adminID)) {
         position[0]++;
         sequenceHandler();
         return;
         }*/

        final VerticalPanel screenPanel = new VerticalPanel(); //used to align mainpanel
        final VerticalPanel mainPanel = new VerticalPanel();   //contains all screen elements
        final Label title = new Label();
        final HorizontalPanel emailSubmit = new HorizontalPanel();
        final HTML emailHTML = new HTML();
        final Label printText = new Label();
        final HorizontalPanel printPanel = new HorizontalPanel();
        final Button printButton = new Button("Print");
        final TextBox emailTextBox = new TextBox();
        final Button emailSubmitButton = new Button("Submit");
        final VerticalPanel emailPanel = new VerticalPanel();
        final Label projectTitleText = new Label();
        final HTML approvalHTML = new HTML();
        final Label contactTitle = new Label();
        final HTML contactHTML = new HTML();
        final HTML infoHTML = new HTML();
        final Button continueButton = new Button("Click here to continue");

        title.setText("Information page for participants in research studies");
        title.setStyleName("titleText");

        printPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        printText.setText("Click here to print this page:");
        printText.setStyleName("rightMarginSmall", true);
        printPanel.add(printText);
        printPanel.add(printButton);

        emailHTML.setHTML("If you would like to receive a copy of this information by email, please enter your email address below:");

        emailTextBox.setStyleName("rightMarginSmall", true);

        emailSubmit.add(emailTextBox);
        emailSubmit.add(emailSubmitButton);

        emailSubmitButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String phpString = "send_info.php?to=" + emailTextBox.getText();
                phpCall(phpString);

                mainPanel.remove(emailPanel);
            }
        });

        emailPanel.add(emailHTML);
        emailPanel.add(emailSubmit);

        projectTitleText.setText("Title of project: Online response time studies of attention and memory");

        approvalHTML.setHTML("This study has been approved by the UCL Research Ethics Committee "
                + "as Project ID Number: 1584/002");

        contactTitle.setText("Name, address and contact details of investigators: ");

        contactHTML.setHTML("Dr Sam Gilbert<br>"
                + "Institute of Cognitive Neuroscience<br>"
                + "17 Queen Square<br>"
                + "London WC1N 3AR<br><br>"
                + "<a href=\"mailto:sam.gilbert@ucl.ac.uk\">sam.gilbert@ucl.ac.uk</a><br>");

        infoHTML.setHTML(infoText());

        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        mainPanel.add(title);
        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        mainPanel.add(printPanel);
        mainPanel.add(emailPanel);
        mainPanel.add(projectTitleText);
        mainPanel.add(approvalHTML);
        mainPanel.add(contactTitle);
        mainPanel.add(contactHTML);
        mainPanel.add(infoHTML);
        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        mainPanel.add(continueButton);
        mainPanel.setSpacing(20);
        mainPanel.setWidth("75%");

        screenPanel.setHeight(Window.getClientHeight() + "px");
        screenPanel.setWidth("100%");
        screenPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        screenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        screenPanel.add(mainPanel);

        printButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Window.print();
            }
        });

        continueButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(screenPanel);
                position[0]++;
                sequenceHandler();
            }
        });

        RootPanel.get().add(screenPanel);

        emailTextBox.setWidth((printText.getOffsetWidth() - 5) + "px");
    }

    public void consentPage() {
        /*if (workerId.equals(adminID)) {
         position[0]++;
         sequenceHandler();
         return;
         }*/

        final CheckBox box1 = new CheckBox("I have read the information page");
        final CheckBox box2 = new CheckBox("I have had the opportunity to contact the "
                + "researcher to ask questions and discuss the study");
        final CheckBox box3 = new CheckBox("I have received satisfactory answers "
                + "to my questions or have been advised of an individual to "
                + "contact for answers to pertinent questions about the research "
                + "and my rights as a participant");
        final CheckBox box4 = new CheckBox("I understand that I am free to "
                + "withdraw at any time, without giving a reason, and without "
                + "incurring any penalty");

        final VerticalPanel mainPanel = new VerticalPanel(); //contains all page elements
        final VerticalPanel screenPanel = new VerticalPanel();   //used to align elements to centre of screen
        final VerticalPanel checkBoxPanel = new VerticalPanel();
        final VerticalPanel namePanel = new VerticalPanel();
        final VerticalPanel emailPanel = new VerticalPanel();
        final VerticalPanel agePanel = new VerticalPanel();
        final VerticalPanel genderPanel = new VerticalPanel();
        final HorizontalPanel genderButtons = new HorizontalPanel();
        final HorizontalPanel buttonPanel = new HorizontalPanel();
        final Button backButton = new Button("Go back to information page");
        final Button agreeButton = new Button("I confirm that I wish to continue");
        final TextBox ageBox = new TextBox();
        final Label ageBoxLabel = new Label("Please enter your age in years: ");
        final Label genderLabel = new Label("Are you: ");
        final RadioButton maleRadioButton = new RadioButton("gender", "male");
        final RadioButton femaleRadioButton = new RadioButton("gender", "female");
        final RadioButton otherRadioButton = new RadioButton("gender", "other");
        final Label projectTitleText = new Label();
        final HTML approvalHTML = new HTML();
        final HTML bodyHTML = new HTML();
        final Label title = new Label();
        final Label pleaseConfirmText = new Label();
        final TextBox emailTextBox = new TextBox();
        final Button emailSubmitButton = new Button("Submit");
        final VerticalPanel emailPanel1 = new VerticalPanel();
        final HorizontalPanel emailSubmit = new HorizontalPanel();
        final HTML emailHTML = new HTML();
        final Label printText = new Label();
        final HorizontalPanel printPanel = new HorizontalPanel();
        final Button printButton = new Button("Print");

        title.setText("Consent form for participants in research studies");
        title.setStyleName("titleText");
        title.setStyleName("bottomMarginSmall", true);

        projectTitleText.setStyleName("bottomMarginSmall", true);
        projectTitleText.setText("Title of project: Online response time studies of attention and memory");

        approvalHTML.setStyleName("bottomMarginSmall", true);
        approvalHTML.setHTML("This study has been approved by the UCL Research Ethics Committee "
                + "as Project ID Number: 1584/002");

        bodyHTML.setStyleName("bottomMarginSmall", true);
        bodyHTML.setHTML("Thank you for your interest in taking part in this research. If "
                + "you have any questions arising from the Information Page that you have "
                + "already seen, please contact the experimenter before you decide whether "
                + "to continue. You can go back to "
                + "the Information Page by clicking the 'Go back to information page' button below.");

        emailHTML.setHTML("If you would like to receive a copy of this consent form by email, please enter your email address here:");

        emailTextBox.setStyleName("rightMarginSmall", true);
        emailTextBox.addStyleName("bottomMarginSmall");

        emailSubmit.add(emailTextBox);
        emailSubmit.add(emailSubmitButton);

        emailSubmitButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String phpString = "send_consent.php?to=" + emailTextBox.getText();
                phpCall(phpString);

                mainPanel.remove(emailPanel1);
            }
        });

        emailPanel1.add(emailHTML);
        emailPanel1.add(emailSubmit);

        printPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        printText.setText("Click here to print this page:");
        printText.setStyleName("rightMarginSmall", true);
        printPanel.add(printText);
        printPanel.add(printButton);

        printPanel.addStyleName("bottomMarginSmall");

        pleaseConfirmText.setText("Please confirm the following: ");

        checkBoxPanel.setSpacing(10);
        checkBoxPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        checkBoxPanel.add(box1);
        checkBoxPanel.add(box2);
        checkBoxPanel.add(box3);
        checkBoxPanel.add(box4);
        //checkBoxPanel.setWidth("500px");

        agePanel.add(ageBoxLabel);
        agePanel.add(ageBox);

        genderPanel.add(genderLabel);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        genderPanel.add(otherRadioButton);

        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        checkBoxPanel.setStyleName("bottomMarginSmall");
        ageBox.setStyleName("bottomMarginSmall");
        //femaleRadioButton.setStyleName("bottom_margin");

        buttonPanel.add(backButton);
        buttonPanel.add(agreeButton);

        mainPanel.setWidth("75%");

        mainPanel.add(title);
        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        mainPanel.add(printPanel);
        mainPanel.add(emailPanel1);
        mainPanel.add(projectTitleText);
        mainPanel.add(approvalHTML);
        mainPanel.add(bodyHTML);
        mainPanel.add(pleaseConfirmText);
        mainPanel.add(checkBoxPanel);
        mainPanel.add(namePanel);
        mainPanel.add(emailPanel);
        mainPanel.add(agePanel);
        mainPanel.add(genderPanel);
        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        mainPanel.add(buttonPanel);

        screenPanel.setHeight(Window.getClientHeight() + "px");
        screenPanel.setWidth("100%");
        screenPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        screenPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        screenPanel.add(mainPanel);

        backButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get().remove(screenPanel);
                position[0]--;
                sequenceHandler();
            }
        });

        agreeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int boxesTicked = 0, validAge = 1, validGender = 0;
                String alertString = "";

                if (ageBox.getText().length() < 2) {
                    validAge = 0;
                }

                if (maleRadioButton.getValue()) {
                    validGender++;
                }
                if (femaleRadioButton.getValue()) {
                    validGender++;
                }
                if (otherRadioButton.getValue()) {
                    validGender++;
                }

                if (box1.getValue()) {
                    boxesTicked++;
                }
                if (box2.getValue()) {
                    boxesTicked++;
                }
                if (box3.getValue()) {
                    boxesTicked++;
                }
                if (box4.getValue()) {
                    boxesTicked++;
                }

                if (boxesTicked + validAge + validGender == 6) {
                    male = maleRadioButton.getValue();
                    female = femaleRadioButton.getValue();
                    other = otherRadioButton.getValue();
                    age = ageBox.getText();

                    String phpString;

                    phpString = "participant_info.php";
                    phpString = phpString + "?participantID=" + workerId;
                    phpString = phpString + "&age=" + age;
                    phpString = phpString + "&male=" + male;
                    phpString = phpString + "&female=" + female;
                    phpString = phpString + "&other=" + other;
                    phpString = phpString + "&counterbalancing=" + counterbalancing;
                    phpString = phpString + "&difficultyGroup=" + difficultyGroup;
                    phpString = phpString + "&feedbackGroup=" + feedbackGroup;

                    phpCall(phpString);
                    
                    RootPanel.get().remove(screenPanel);

                    if (Integer.parseInt(ageBox.getText()) < 18) {
                        under18();
                    } else {
                        position[0]++;
                        sequenceHandler();
                    }
                } else {
                    if (boxesTicked < 4) {
                        alertString = alertString + "You must tick all boxes to continue\n";
                    }

                    if (validAge == 0) {
                        alertString = alertString + "Please enter your age\n";
                    }

                    if (validGender == 0) {
                        alertString = alertString + "Please indicate your gender";
                    }

                    Window.alert(alertString);
                }
            }
        });

        RootPanel.get().add(screenPanel);
        ageBox.setWidth(ageBoxLabel.getOffsetWidth() + "px");
        emailTextBox.setWidth((printText.getOffsetWidth() - 5) + "px");
    }

    public void under18() {
        Label textLabel = new Label("Sorry but you must be over 18 to take part.");

        RootPanel.get().add(textLabel);
    }

    public void checkIPaddress() {
        if (workerId.equals(adminID)) {
            position[0]++;
            sequenceHandler();
            return;
        }

        String phpText = "../php/checkIP.php?dbname=ucjtw3u_" + IDexperimentCode;

        final HTML errorText = new HTML();
        final VerticalPanel mainPanel = new VerticalPanel();

        errorText.setHTML(IP_errorText());
        mainPanel.add(errorText);
        mainPanel.setWidth("75%");

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, phpText);

        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    Window.alert("Could not connect to server");
                }

                public void onResponseReceived(Request request, Response response) {
                    if (response.getText().equals("valid")) {
                        position[0]++;
                        sequenceHandler();
                    } else {
                        RootPanel.get().add(mainPanel);
                    }
                }
            });
        } catch (RequestException e) {
            Window.alert("no connection to server");
        }
    }

    public void checkParticipantID() {
        if (workerId.equals(adminID)) {
            position[0]++;
            sequenceHandler();
            return;
        }

        String phpText = "../php/checkID.php?dbname=ucjtw3u_" + IDexperimentCode + "&id=";
        phpText = phpText + workerId;

        final HTML errorText = new HTML();
        final VerticalPanel mainPanel = new VerticalPanel();

        errorText.setHTML(ID_errorText());
        mainPanel.add(errorText);
        mainPanel.setWidth("75%");

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, phpText);

        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    Window.alert("Could not connect to server");
                }

                public void onResponseReceived(Request request, Response response) {
                    if (response.getText().equals("valid")) {
                        position[0]++;
                        sequenceHandler();
                    } else {
                        RootPanel.get().add(mainPanel);
                    }
                }
            });
        } catch (RequestException e) {
            Window.alert("no connection to server");
        }
    }

    public void setRewardCode() {
        final HTML workerError = new HTML("You must accept the HIT before you can continue. "
                + "Please close this page, accept the HIT, and try again."
                + "<br><br>If you have already accepted the HIT there has been a JavaScript "
                + "error which means you will not be able to continue. Apologies.");

        try {
            if (workerId.startsWith("A")) {
                //generate reward code
                for (int i = 0; i < 7; i++) {
                    rewardCode = rewardCode + (Random.nextInt(9) + 1);
                }

                //save reward code to database
                String phpString = "rewardCode.php?participantID=";
                phpString = phpString + workerId + "&code=" + rewardCode;

                phpCall(phpString);

                position[0]++;
                sequenceHandler();
            } else {
                String phpString = "rewardCode.php?participantID=";
                phpString = phpString + workerId + "&code=" + rewardCode;

                phpCall(phpString);

                RootPanel.get().add(workerError);
            }
        } catch (Throwable t) {
            String phpString = "rewardCode.php?participantID=999999&code=" + rewardCode;

            phpCall(phpString);

            RootPanel.get().add(workerError);
        }
    }

    public String phpCall(String phpText) {
        phpOutput = null;

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, phpText);

        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    Window.alert("Could not connect to server");
                }

                public void onResponseReceived(Request request, Response response) {
                    /*if (response.getText().length()>0) {
                     Window.alert(response.getText());
                     }*/
                    phpOutput = response.getText();
                }
            });
        } catch (RequestException e) {
            Window.alert("no connection to server");
        }

        return (phpOutput);
    }

    class TrialParams {

        int blockNum;
        int trialNum;
        int nCircles;
        int totalCircles;
        String[] labels;
        String instructions;
        boolean cuedTargets;
        boolean showClock;
        int[] targets;
    }

    class BlockParams {

        int blockNum;
        int nTrials;
        int nCircles;
        int totalCircles;
        int alphabetOrder;
        int defaultExit;
        int nPM;
        int totalTargets;
        boolean cuedTargets;
        boolean showClock;
        boolean moveAny;
        int pmTiming;
	}
}
