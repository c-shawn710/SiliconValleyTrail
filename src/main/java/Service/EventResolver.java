package Service;

import Enums.WeatherType;
import Model.Event;
import Model.EventChoice;
import Model.Location;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventResolver {

    private final Random random;

    public EventResolver() {
        this.random = new Random();
    }

    public Event resolveEvent(Location location, WeatherType weatherType) {
        List<Event> validEvents = new ArrayList<>();

        String locationName = location.getName();

        switch (locationName) {
            case "San Jose":
                validEvents.add(createGarageSaleUpdate());
                validEvents.add(createLocalNetworkingNight());
                addIfEligible(validEvents, createFounderMeetUp(), weatherType);
                break;

            case "Santa Clara":
                validEvents.add(createVCPitchOpportunity());
                validEvents.add(createOfficeSupplyRun());
                addIfEligible(validEvents, createCommuteChaos(), weatherType);
                break;

            case "Sunnyvale":
                validEvents.add(createCloudCreditsOffer());
                validEvents.add(createTeamBrainstormSession());
                addIfEligible(validEvents, createHeatwaveBurnout(), weatherType);
                break;

            case "Mountain View":
                validEvents.add(createProductionOutage());
                validEvents.add(createMentorSession());
                addIfEligible(validEvents, createDemoSetupConfusion(), weatherType);
                break;

            case "Palo Alto":
                validEvents.add(createInvestorCoffeeChat());
                validEvents.add(createFounderPanelTalk());
                break;

            case "Menlo Park":
                validEvents.add(createCustomerFeedbackSession());
                validEvents.add(createMiniProductLaunch());
                addIfEligible(validEvents, createTeamSyncMeeting(), weatherType);
                break;

            case "Redwood City":
                validEvents.add(createEnterprisePilotDeal());
                validEvents.add(createLogisticsShortcut());
                break;

            case "San Bruno":
                validEvents.add(createApiRateLimitIncident());
                validEvents.add(createPreDemoPrep());
                addIfEligible(validEvents, createRainyRemoteDay(), weatherType);
                break;

            case "Daly City":
                validEvents.add(createRecruitmentPitch());
                validEvents.add(createPreDemoPrep());
                addIfEligible(validEvents, createWindyDemoPrep(), weatherType);
                break;

            default:
                validEvents.add(createFallbackSnackTable());
                validEvents.add(createFallbackPressMention());
                break;
        }

        return validEvents.get(random.nextInt(validEvents.size()));
    }

    private void addIfEligible(List<Event> validEvents, Event event, WeatherType weatherType) {
        if (event.isWeatherEligible(weatherType)) {
            validEvents.add(event);
        }
    }

    private Event createGarageSaleUpdate() {
        return new Event(
                "Garage Sale Update",
                "Your team spots discounted office gear and secondhand hardware.",
                EventChoice.builder("Buy discounted gear")
                        .cash(-3000)
                        .morale(4)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Pass")
                        .morale(-2)
                        .coffee(2)
                        .build()
        );
    }

    private Event createLocalNetworkingNight() {
        return new Event(
                "Local Networking Night",
                "A casual startup meetup could build momentum or cost focus.",
                EventChoice.builder("Attend")
                        .cash(0)
                        .morale(3)
                        .coffee(0)
                        .bugs(2)
                        .build(),
                EventChoice.builder("Stay Focused")
                        .cash(0)
                        .morale(-2)
                        .coffee(-2)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createFounderMeetUp() {
        return new Event(
                "Founder Meetup",
                "Clear weather makes an outdoor founder meetup possible.",
                EventChoice.builder("Attend")
                        .cash(2000)
                        .morale(3)
                        .coffee(3)
                        .bugs(2)
                        .build(),
                EventChoice.builder("Skip")
                        .cash(0)
                        .morale(-5)
                        .coffee(-3)
                        .bugs(-1)
                        .build(),
                WeatherType.CLEAR
        );
    }

    private Event createVCPitchOpportunity() {
        return new Event(
                "Venture Capital Pitch Opportunity",
                "A VC offers your team a short pitch slot!",
                EventChoice.builder("Take pitch")
                        .cash(8000)
                        .morale(3)
                        .coffee(6)
                        .bugs(2)
                        .build(),
                EventChoice.builder("Skip")
                        .cash(0)
                        .morale(-5)
                        .coffee(-3)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createOfficeSupplyRun() {
        return new Event(
                "Office Supply Run",
                "You can spend some cash to stabilize the team and clean things up.",
                EventChoice.builder("Spend on upgrades")
                        .cash(-2000)
                        .morale(5)
                        .coffee(0)
                        .bugs(-1)
                        .build(),
                EventChoice.builder("Save money")
                        .cash(0)
                        .morale(-2)
                        .coffee(-2)
                        .bugs(0)
                        .build()
        );
    }

    private Event createCommuteChaos() {
        return new Event(
                "Commute Chaos",
                "Rain turns local travel into a mess.",
                EventChoice.builder("Pay for ride-share")
                        .cash(-15000)
                        .morale(15)
                        .coffee(0)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Tough commute")
                        .cash(0)
                        .morale(-25)
                        .coffee(-5)
                        .bugs(0)
                        .build()
        );
    }

    private Event createCloudCreditsOffer() {
        return new Event(
                "Cloud Credits Offer",
                "A vendor offers cloud credits if you sit through a sales demo.",
                EventChoice.builder("Attend demo")
                        .cash(5000)
                        .morale(5)
                        .coffee(-2)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Skip")
                        .cash(0)
                        .morale(-5)
                        .coffee(0)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createTeamBrainstormSession() {
        return new Event(
                "Team Brainstorm Session",
                "The team wants to spend time exploring new ideas.",
                EventChoice.builder("Brainstorm")
                        .cash(0)
                        .morale(2)
                        .coffee(0)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Focus on execution")
                        .cash(0)
                        .morale(-5)
                        .coffee(-3)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createHeatwaveBurnout() {
        return new Event(
                "Heatwave Burnout",
                "Hot weather is draining the team fast.",
                EventChoice.builder("Buy cold brew")
                        .cash(-2000)
                        .morale(10)
                        .coffee(6)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Push through")
                        .cash(0)
                        .morale(-8)
                        .coffee(0)
                        .bugs(-2)
                        .build(),
                WeatherType.HEAT
        );
    }

    private Event createProductionOutage() {
        return new Event(
                "Production Outage",
                "A visible outage hits your product at the worst time possible.",
                EventChoice.builder("Emergency fix")
                        .cash(0)
                        .morale(-5)
                        .coffee(-8)
                        .bugs(-2)
                        .build(),
                EventChoice.builder("Delay response")
                        .cash(0)
                        .morale(0)
                        .coffee(0)
                        .bugs(1)
                        .build()
        );
    }

    private Event createMentorSession() {
        return new Event(
                "Mentor Session",
                "An experienced engineer offers quick advice.",
                EventChoice.builder("Take advice")
                        .cash(0)
                        .morale(8)
                        .coffee(-5)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Ignore")
                        .cash(0)
                        .morale(-10)
                        .coffee(3)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createDemoSetupConfusion() {
        return new Event(
                "Demo Setup Confusion",
                "Foggy conditions make the setup process chaotic.",
                EventChoice.builder("Double-check")
                        .cash(0)
                        .morale(-3)
                        .coffee(0)
                        .bugs(-1)
                        .build(),
                EventChoice.builder("Hope for the best")
                        .cash(0)
                        .morale(-5)
                        .coffee(5)
                        .bugs(2)
                        .build(),
                WeatherType.FOG
        );
    }

    private Event createInvestorCoffeeChat() {
        return new Event(
                "Investor Coffee Chat",
                "A local investor offers a quick coffee chat.",
                EventChoice.builder("Take meeting")
                        .cash(6000)
                        .morale(10)
                        .coffee(-4)
                        .bugs(0)
                        .build(),
                EventChoice.builder("Decline")
                        .cash(0)
                        .morale(-5)
                        .coffee(2)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createFounderPanelTalk() {
        return new Event(
                "Founder Panel Talk",
                "A panel appearance could help your startup get noticed.",
                EventChoice.builder("Participate")
                        .cash(-5000)
                        .morale(8)
                        .coffee(-6)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Skip")
                        .cash(0)
                        .morale(-10)
                        .coffee(5)
                        .bugs(-2)
                        .build()
        );
    }

    private Event createEnterprisePilotDeal() {
        return new Event(
                "Enterprise Pilot Deal",
                "A nearby company is interested in testing your product.",
                EventChoice.builder("Accept")
                        .cash(10000)
                        .morale(15)
                        .coffee(-10)
                        .bugs(2)
                        .build(),
                EventChoice.builder("Decline")
                        .cash(0)
                        .morale(-10)
                        .coffee(5)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createLogisticsShortcut() {
        return new Event(
                "Logistics Shortcut",
                "A faster route could save resources but strain the team",
                EventChoice.builder("The team can handle it")
                        .cash(-5000)
                        .morale(-10)
                        .coffee(-2)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Play it safe")
                        .cash(0)
                        .morale(0)
                        .coffee(-8)
                        .bugs(-2)
                        .build()
        );
    }

    private Event createCustomerFeedbackSession() {
        return new Event(
                "Customer Feedback Session",
                "A small group of users offer quality feedback.",
                EventChoice.builder("Receive feedback")
                        .cash(0)
                        .morale(-15)
                        .coffee(-6)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Talk to the hand")
                        .cash(0)
                        .morale(0)
                        .coffee(5)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createMiniProductLaunch() {
        return new Event(
                "Mini Product Launch",
                "You could push a smaller release and test the waters.",
                EventChoice.builder("Launch")
                        .cash(0)
                        .morale(5)
                        .coffee(-3)
                        .bugs(2)
                        .build(),
                EventChoice.builder("Delay")
                        .cash(0)
                        .morale(-3)
                        .coffee(2)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createApiRateLimitIncident() {
        return new Event(
                "API Rate Limit Incident",
                "A third-party dependency is throttling your request,",
                EventChoice.builder("Patch it")
                        .cash(0)
                        .morale(-15)
                        .coffee(-8)
                        .bugs(-1)
                        .build(),
                EventChoice.builder("Wait")
                        .cash(0)
                        .morale(-3)
                        .coffee(-2)
                        .bugs(1)
                        .build()
        );
    }

    private Event createTeamSyncMeeting() {
        return new Event(
                "Team Sync Meeting",
                "A focused sync could reduce confusion",
                EventChoice.builder("Align team")
                        .cash(0)
                        .morale(10)
                        .coffee(-6)
                        .bugs(-1)
                        .build(),
                EventChoice.builder("Reschedule")
                        .cash(0)
                        .morale(-3)
                        .coffee(3)
                        .bugs(0)
                        .build(),
                WeatherType.CLEAR
        );
    }

    private Event createRainyRemoteDay() {
        return new Event(
                "Rainy Remote Day",
                "Rain makes remote work look more appealing.",
                EventChoice.builder("Work from home")
                        .cash(0)
                        .morale(5)
                        .coffee(8)
                        .bugs(2)
                        .build(),
                EventChoice.builder("Commute")
                        .cash(0)
                        .morale(-15)
                        .coffee(-6)
                        .bugs(1)
                        .build(),
                WeatherType.RAIN
        );
    }

    private Event createRecruitmentPitch() {
        return new Event(
                "Recruitment Pitch",
                "A strong engineer is interested in joining the team.",
                EventChoice.builder("Hire engineer")
                        .cash(-5000)
                        .morale(10)
                        .coffee(-5)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Pass")
                        .cash(0)
                        .morale(0)
                        .coffee(3)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createPreDemoPrep() {
        return new Event(
                "Pre-Demo Prep",
                "You can either practice or keep the team loose.",
                EventChoice.builder("Practice")
                        .cash(0)
                        .morale(5)
                        .coffee(-3)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Wing it")
                        .cash(0)
                        .morale(0)
                        .coffee(5)
                        .bugs(-1)
                        .build()
        );
    }

    private Event createWindyDemoPrep() {
        return new Event(
                "Windy Demo Prep",
                "The wind complicates your final demo setup.",
                EventChoice.builder("Rent indoor space")
                        .cash(-10000)
                        .morale(5)
                        .coffee(-3)
                        .bugs(1)
                        .build(),
                EventChoice.builder("Deal with it")
                        .cash(0)
                        .morale(-8)
                        .coffee(4)
                        .bugs(-1)
                        .build(),
                WeatherType.CLEAR
        );
    }

    private Event createFallbackSnackTable() {
        return new Event(
                "Free Snack Table",
                "You stumble across free snacks and drinks.",
                EventChoice.builder("Stock up")
                        .cash(0)
                        .morale(15)
                        .coffee(8)
                        .bugs(4)
                        .build(),
                EventChoice.builder("Ignore")
                        .cash(0)
                        .morale(-10)
                        .coffee(-6)
                        .bugs(-2)
                        .build()
        );
    }

    private Event createFallbackPressMention() {
        return new Event(
                "Press Mention",
                "A local blog noticed your startup.",
                EventChoice.builder("Share it")
                        .cash(2000)
                        .morale(10)
                        .coffee(-5)
                        .bugs(2)
                        .build(),
                EventChoice.builder("Stay focused")
                        .cash(0)
                        .morale(0)
                        .coffee(4)
                        .bugs(-2)
                        .build()
        );
    }
}
