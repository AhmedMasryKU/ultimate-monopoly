package game;

import game.card.deed.TitleDeed;
import game.square.*;
import game.square.action.*;
import game.square.paycorner.*;
import game.square.tradable.*;

public class Board {

	private final int[] OUTER_TRACK_TRANSIT_LOCATIONS = { 21, 35 };
	private final int[] MIDDLE_TRACK_TRANSIT_LOCATIONS = { 5, 15, 25, 35 };
	private final int[] INNER_TRACK_TRANSIT_LOCATIONS = { 9, 21 };

	private Track outerTrack;
	private Track middleTrack;
	private Track innerTrack;
	private static Board board;

	private Board() {
		outerTrack = new Track(TrackType.OUTER_TRACK);
		middleTrack = new Track(TrackType.MIDDLE_TRACK);
		innerTrack = new Track(TrackType.INNER_TRACK);

		outerTrack.setTransitlocations(OUTER_TRACK_TRANSIT_LOCATIONS);
		middleTrack.setTransitlocations(MIDDLE_TRACK_TRANSIT_LOCATIONS);
		innerTrack.setTransitlocations(INNER_TRACK_TRANSIT_LOCATIONS);
		constructSquares();
	}

	public static Board getInstance() {
		if (board == null) {
			board = new Board();
		}
		return board;
	}

	public void constructSquares() {
		//mortgage values will be added.

		//The removed squares are replaced with free parking
		FreeParkingSquare freeParking1Track1 = new FreeParkingSquare ("Free Parking", 1);
		outerTrack.addSquare(freeParking1Track1);
		TitleDeed lakeStreetTD = new TitleDeed(1, 5, 15, 45, 80, 125, 625, 15, 50, 50, 50);
		PropertySquare lakeStreet = new PropertySquare("Lake Street", 2, 30, null);
		outerTrack.addSquare(lakeStreet);
		CommunityChestSquare communityChest1outerTrack = new CommunityChestSquare ("Community Chest", 3);
		outerTrack.addSquare(communityChest1outerTrack);
		TitleDeed nicolletAveTD = new TitleDeed(1, 5, 15, 45, 80, 125, 625, 15, 50, 50, 50);
		PropertySquare nicolletAve = new PropertySquare("Nicollet Avenue", 4, 30, null);
		outerTrack.addSquare(nicolletAve);		
		TitleDeed hennepinAveTD = new TitleDeed(3, 15, 45, 120, 240, 350, 850, 30, 50, 50, 50);
		PropertySquare hennepinAve = new PropertySquare("Hennepin Avenue", 5, 60, null);
		outerTrack.addSquare(hennepinAve);
		FreeParkingSquare freeParking2Track1 = new FreeParkingSquare ("Free Parking", 6);
		outerTrack.addSquare(freeParking2Track1);
		FreeParkingSquare freeParking3Track1 = new FreeParkingSquare ("Free Parking", 7);
		outerTrack.addSquare(freeParking3Track1);
		// Reading TransitStation-Railroad 
		TitleDeed esplanadeAveTD = new TitleDeed(5, 25, 80, 225, 360, 600, 1000, 50, 50, 50, 50);
		PropertySquare esplanadeAve = new PropertySquare("Esplanade Avenue", 9, 90, null);
		outerTrack.addSquare(esplanadeAve);
		TitleDeed canalStreetTD = new TitleDeed(5, 25, 80, 225, 360, 600, 1000, 50, 50, 50, 50);
		PropertySquare canalStreet = new PropertySquare("Canal Street", 10, 90, null);
		outerTrack.addSquare(canalStreet);
		ChanceSquare chanceSquare1outerTrack = new ChanceSquare("Chance", 11);
		outerTrack.addSquare(chanceSquare1outerTrack);
		//				Utility Squares will be modified.
		//				UtilitySquare cableCompany = new UtilitySquare ("Cable Company", 12, 150, null);
		//				outerTrack.addSquare(cableCompany);
		TitleDeed magazineStreetTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare magazineStreet = new PropertySquare("Magazine Street",13, 120, null);
		outerTrack.addSquare(magazineStreet);
		TitleDeed bourbonStreetTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare bourbonStreet = new PropertySquare("Bourbon Street", 14, 120, null);
		outerTrack.addSquare(bourbonStreet);
		HollandTunnelSquare hollandTunnel1 = new HollandTunnelSquare("Holland Tunnel", 15);
		outerTrack.addSquare(hollandTunnel1);
		FreeParkingSquare freeParking4Track1 = new FreeParkingSquare ("Free Parking", 16);
		outerTrack.addSquare(freeParking4Track1);
		TitleDeed katyFreewayTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 70, 100, 100, 100);
		PropertySquare katyFreeway = new PropertySquare("Katy Freeway", 17, 150, null);
		outerTrack.addSquare(katyFreeway);
		TitleDeed westheimerRoadTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 70, 100, 100, 100);
		PropertySquare westheimerRoad = new PropertySquare("Westheimer Road", 18, 150, null);
		outerTrack.addSquare(westheimerRoad);
		//				UtilitySquare internetServiceProvider = new UtilitySquare ("Internet Service Provider", 19, 150, null);
		//				outerTrack.addSquare(internetServiceProvider);
		TitleDeed kirbyDrTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 80, 100, 100, 100);
		PropertySquare kirbyDr = new PropertySquare("Kirby Drive", 20, 180, null);
		outerTrack.addSquare(kirbyDr);
		TitleDeed cullenBlvdTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 80, 100, 100, 100);
		PropertySquare cullenBlvd = new PropertySquare("Cullen Boulevard", 21, 180, null);
		outerTrack.addSquare(cullenBlvd);
		ChanceSquare chanceSquare2outerTrack = new ChanceSquare("Chance", 22);
		outerTrack.addSquare(chanceSquare2outerTrack);
		FreeParkingSquare freeParking5Track1 = new FreeParkingSquare ("Free Parking", 23);
		outerTrack.addSquare(freeParking5Track1);
		TitleDeed dekalbAveTD = new TitleDeed(17, 85, 240, 670, 840, 1025, 1525, 90, 100, 100, 100);
		PropertySquare dekalbAve = new PropertySquare("Dekalb Avenue", 24, 210, null);
		outerTrack.addSquare(dekalbAve);
		CommunityChestSquare communityChest2outerTrack = new CommunityChestSquare ("Community Chest", 25);
		outerTrack.addSquare(communityChest2outerTrack);
		TitleDeed andrewYoungIntlBlvdTD = new TitleDeed(17, 85, 240, 670, 840, 1025, 1525, 90, 100, 100, 100);
		PropertySquare andrewYoungIntlBlvd = new PropertySquare("Andrew Young Intl Boulevard", 26, 210, null);
		outerTrack.addSquare(andrewYoungIntlBlvd);
		TitleDeed decaturStTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 1600, 100, 100, 100, 100);
		PropertySquare decaturSt = new PropertySquare("Decatur Street", 27, 240, null);
		outerTrack.addSquare(decaturSt);
		TitleDeed peachtreeStTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 1600, 100, 100, 100, 100);
		PropertySquare peachtreeSt = new PropertySquare("Peachtree Street", 28, 240, null);
		outerTrack.addSquare(peachtreeSt);
		//				PayDaySquare payDay = new PayDaySquare("PAY DAY", 29);
		//				outerTrack.addSquare(payDay);
		TitleDeed randolphStTD = new TitleDeed(23, 115, 345, 825, 1010, 1180, 2180, 110, 150, 150, 150);
		PropertySquare randolphSt = new PropertySquare("Randolph Street", 30, 270, null);
		outerTrack.addSquare(randolphSt);
		ChanceSquare chanceSquare3outerTrack = new ChanceSquare("Chance", 31);
		outerTrack.addSquare(chanceSquare3outerTrack);
		TitleDeed lakeShoreDrTD = new TitleDeed(23, 115, 345, 825, 1010, 1180, 2180, 110, 150, 150, 150);
		PropertySquare lakeShoreDr = new PropertySquare("Lake Shore Drive", 32, 270, null);
		outerTrack.addSquare(lakeShoreDr);
		TitleDeed wackerDrTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 120, 150, 150, 150);
		PropertySquare wackerDr = new PropertySquare("Wacker Drive", 33, 300, null);
		outerTrack.addSquare(wackerDr);
		TitleDeed michiganAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 120, 150, 150, 150);
		PropertySquare michiganAve = new PropertySquare("Michigan Avenue", 34, 300, null);
		outerTrack.addSquare(michiganAve);
		FreeParkingSquare freeParking6Track1 = new FreeParkingSquare ("Free Parking", 35);
		outerTrack.addSquare(freeParking6Track1);
		// B&O Railroad/TransitStation
		CommunityChestSquare communityChest3outerTrack = new CommunityChestSquare ("Community Chest", 37);
		outerTrack.addSquare(communityChest3outerTrack);
		TitleDeed southTempleTD = new TitleDeed(32, 160, 470, 1050, 1250, 1500, 2500, 130, 200, 200, 200);
		PropertySquare southTemple = new PropertySquare("South Temple", 38, 330, null);
		outerTrack.addSquare(southTemple);
		TitleDeed westTempleTD = new TitleDeed(32, 160, 470, 1050, 1250, 1500, 2500, 130, 200, 200, 200);
		PropertySquare westTemple = new PropertySquare("West Temple", 39, 330, null);
		outerTrack.addSquare(westTemple);
		//				UtilitySquare trashCollector = new UtilitySquare ("Trash Collector", 40, 150, null);
		//				outerTrack.addSquare(trashCollector);
		TitleDeed northTempleTD = new TitleDeed(38, 170, 520, 1125, 1425, 1275, 1650, 140, 200, 200, 200);
		PropertySquare northTemple = new PropertySquare("North Temple", 41, 360, null);
		outerTrack.addSquare(northTemple);
		TitleDeed templeSquareTD = new TitleDeed(38, 170, 520, 1125, 1425, 1275, 1650, 140, 200, 200, 200);
		PropertySquare templeSquare = new PropertySquare("Temple Square", 42, 360, null);
		outerTrack.addSquare(templeSquare);
		SubwaySquare subway = new SubwaySquare ("Subway", 43);
		outerTrack.addSquare(subway);
		TitleDeed southStTD = new TitleDeed(45, 210, 575, 1300, 1600, 1800, 3300, 150, 250, 250, 250);
		PropertySquare southSt = new PropertySquare("South Street", 44, 390, null);
		outerTrack.addSquare(southSt);
		TitleDeed broadStTD = new TitleDeed(45, 210, 575, 1300, 1600, 1800, 3300, 150, 250, 250, 250);
		PropertySquare broadSt = new PropertySquare("Broad Street", 45, 390, null);
		outerTrack.addSquare(broadSt);
		TitleDeed walnutStTD = new TitleDeed(55, 225, 630, 1450, 1750, 2050, 3550, 160, 250, 250, 250);
		PropertySquare walnutSt = new PropertySquare("Walnut Street", 46, 420, null);
		outerTrack.addSquare(walnutSt);
		CommunityChestSquare communityChest4outerTrack = new CommunityChestSquare ("Community Chest", 47);
		outerTrack.addSquare(communityChest4outerTrack);
		TitleDeed marketStTD = new TitleDeed(55, 225, 630, 1450, 1750, 2050, 3550, 160, 250, 250, 250);
		PropertySquare marketSt = new PropertySquare("Market Street", 48, 420, null);
		outerTrack.addSquare(marketSt);
		FreeParkingSquare freeParking7Track1 = new FreeParkingSquare ("Free Parking", 49);
		outerTrack.addSquare(freeParking7Track1);
		//				UtilitySquare sewageSystem = new UtilitySquare ("Sewage System", 50, 150, null);
		//				outerTrack.addSquare(sewageSystem);
		FreeParkingSquare freeParking8Track1 = new FreeParkingSquare ("Free Parking", 51);
		outerTrack.addSquare(freeParking8Track1);
		BirthGiftSquare birthdayGift = new BirthGiftSquare ("Birthday Gift", 52);
		outerTrack.addSquare(birthdayGift);
		TitleDeed mulhollandDrTD = new TitleDeed(70, 350, 750, 1600, 1850, 2100, 3600, 175, 300, 300, 300);
		PropertySquare mulhollandDr = new PropertySquare("Mulholland Drive", 53, 450, null);
		outerTrack.addSquare(mulhollandDr);
		TitleDeed venturaBlvdTD = new TitleDeed(80, 400, 825, 1800, 2175, 2550, 4050, 200, 300, 300, 300);
		PropertySquare venturaBlvd = new PropertySquare("Ventura Boulevard", 54, 480, null);
		outerTrack.addSquare(venturaBlvd);
		ChanceSquare chanceSquare4outerTrack = new ChanceSquare("Chance", 55);
		outerTrack.addSquare(chanceSquare4outerTrack);
		TitleDeed rodeoDrTD = new TitleDeed(90, 450, 900, 2000, 2500, 3000, 4500, 250, 300, 300, 300);
		PropertySquare rodeoDr = new PropertySquare("Rodeo Drive", 56, 510, null);
		outerTrack.addSquare(rodeoDr);

		GoSquare go = new GoSquare("GO", 1);
		middleTrack.addSquare(go);
		TitleDeed mediterraneanAveTD = new TitleDeed(2, 10, 30, 90, 160, 250, 750, 30, 50, 50, 50);
		PropertySquare mediterraneanAve = new PropertySquare("Mediterranean Avenue", 2, 60, null);
		middleTrack.addSquare(mediterraneanAve);
		CommunityChestSquare communityChest1middleTrack = new CommunityChestSquare ("Community Chest", 3);
		middleTrack.addSquare(communityChest1middleTrack);
		TitleDeed balticAveTD = new TitleDeed(4, 20, 60, 180, 320, 450, 900, 30, 50, 50, 50);
		PropertySquare balticAve = new PropertySquare("Baltic Avenue", 4, 60, null);
		middleTrack.addSquare(balticAve);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 5);
		// squares.add(incomeTax1);
		// Reading TransitStation-Railroad 
		TitleDeed orientalAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare orientalAve = new PropertySquare("Oriental Avenue", 7, 100, null);
		middleTrack.addSquare(orientalAve);
		ChanceSquare chanceSquare1middleTrack = new ChanceSquare("Chance", 8);
		middleTrack.addSquare(chanceSquare1middleTrack);
		TitleDeed vermontAveTD = new TitleDeed(6, 30, 90, 270, 400, 550, 1050, 50, 50, 50, 50);
		PropertySquare vermontAve = new PropertySquare("Vermont Avenue", 9, 100, null);
		middleTrack.addSquare(vermontAve);
		TitleDeed connecticutAveTD = new TitleDeed(8, 40, 100, 300, 450, 600, 1100, 60, 50, 50, 50);
		PropertySquare connecticutAve = new PropertySquare("Connecticut Avenue", 10, 120, null);
		middleTrack.addSquare(connecticutAve);
		JailSquare jailSquare = new JailSquare("Jail", 11);
		middleTrack.addSquare(jailSquare);
		TitleDeed stCharlesPlaceTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		PropertySquare stCharlesPlace = new PropertySquare("St. Charles Place", 12, 140, null);
		middleTrack.addSquare(stCharlesPlace);
		//				Utility Squares will be modified.
		//				UtilitySquare electricCompany = new UtilitySquare ("Electric Company", 13, 150, null);
		//				middleTrack.addSquare(electricCompany);
		TitleDeed statesAveTD = new TitleDeed(10, 50, 150, 450, 625, 750, 1250, 70, 100, 100, 100);
		PropertySquare statesAve = new PropertySquare("States Avenue", 14, 140, null);
		middleTrack.addSquare(statesAve);
		TitleDeed virginiaAveTD = new TitleDeed(12, 60, 180, 500, 700, 900, 1400, 80, 100, 100, 100);
		PropertySquare virginiaAve = new PropertySquare("Virginia Avenue", 15, 160, null);
		middleTrack.addSquare(virginiaAve);
		// Pennsylvania Railroad/TransitStation
		TitleDeed stJamesPlaceTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		PropertySquare stJamesPlace = new PropertySquare("St. James Place", 17, 180, null);
		middleTrack.addSquare(stJamesPlace);
		CommunityChestSquare communityChest2middleTrack = new CommunityChestSquare ("Community Chest", 18);
		middleTrack.addSquare(communityChest2middleTrack);
		TitleDeed tennesseeAveTD = new TitleDeed(14, 70, 200, 550, 750, 950, 1450, 90, 100, 100, 100);
		PropertySquare tennesseeAve = new PropertySquare("Tennessee Avenue", 19, 180, null);
		middleTrack.addSquare(tennesseeAve);
		TitleDeed newYorkAveTD = new TitleDeed(16, 80, 220, 600, 800, 1000, 1500, 100, 100, 100, 100);
		PropertySquare newYorkAve = new PropertySquare("New York Avenue", 20, 200, null);
		middleTrack.addSquare(newYorkAve);
		FreeParkingSquare freeParking1Track2 = new FreeParkingSquare ("Free Parking", 21);		
		TitleDeed kentuckyAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		PropertySquare kentuckyAve = new PropertySquare("Kentucky Avenue", 22, 220, null);
		middleTrack.addSquare(kentuckyAve);
		ChanceSquare chanceSquare2middleTrack = new ChanceSquare("Chance", 23);
		middleTrack.addSquare(chanceSquare2middleTrack);
		TitleDeed indianaAveTD = new TitleDeed(18, 90, 250, 700, 875, 1050, 2050, 100, 150, 150, 150);
		PropertySquare indianaAve = new PropertySquare("Indiana Avenue", 24, 220, null);
		middleTrack.addSquare(indianaAve);
		TitleDeed illinoisAveTD = new TitleDeed(20, 100, 300, 750, 925, 1100, 2100, 120, 150, 150, 150);
		PropertySquare illinoisAve = new PropertySquare("Illinois Avenue", 25, 240, null);
		middleTrack.addSquare(illinoisAve);
		// B&O TransitStation/Railroad
		TitleDeed atlanticAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		PropertySquare atlanticAve = new PropertySquare("Atlantic Avenue", 27, 260, null);
		middleTrack.addSquare(atlanticAve);
		TitleDeed ventnorAveTD = new TitleDeed(22, 110, 330, 800, 975, 1150, 2150, 130, 150, 150, 150);
		PropertySquare ventnorAve = new PropertySquare("Ventnor Avenue", 28, 260, null);
		middleTrack.addSquare(ventnorAve);
		//				UtilitySquare waterWorks = new UtilitySquare ("Water Works", 29, 150, null);
		//				middleTrack.addSquare(waterWorks);
		TitleDeed marvinGardensTD = new TitleDeed(24, 120, 350, 850, 1025, 1200, 2200, 140, 150, 150, 150);
		PropertySquare marvinGardens = new PropertySquare("Marvin Gardens", 30, 280, null);
		middleTrack.addSquare(marvinGardens);
		GoToJailSquare goToJail = new GoToJailSquare("Go to Jail", 31);
		middleTrack.addSquare(goToJail);
		TitleDeed pacificAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		PropertySquare pacificAve = new PropertySquare("Pacific Avenue", 32, 300, null);
		middleTrack.addSquare(pacificAve);
		TitleDeed noCarolinaAveTD = new TitleDeed(26, 130, 390, 900, 1100, 1275, 2275, 150, 200, 200, 200);
		PropertySquare noCarolinaAve = new PropertySquare("North Carolina Avenue", 33, 300, null);
		middleTrack.addSquare(noCarolinaAve);
		CommunityChestSquare communityChest3middleTrack = new CommunityChestSquare ("Community Chest", 34);
		middleTrack.addSquare(communityChest3middleTrack);
		TitleDeed pennsylvaniaAveTD = new TitleDeed(28, 150, 450, 1000, 1200, 1400, 2400, 160, 200, 200, 200);
		PropertySquare pennsylvaniaAve = new PropertySquare("Pennsylvania Avenue", 35, 300, null);
		middleTrack.addSquare(pennsylvaniaAve);
		//Short Line Railroad/TransitStation
		ChanceSquare chanceSquare3middleTrack = new ChanceSquare("Chance", 37);
		middleTrack.addSquare(chanceSquare3middleTrack);
		TitleDeed parkPlaceTD = new TitleDeed(35, 175, 500, 1100, 1300, 1500, 2500, 200, 200, 200, 200);
		PropertySquare parkPlace = new PropertySquare("Park Place", 38, 350, null);
		middleTrack.addSquare(parkPlace);
		// IncomeTaxSquare incomeTax1 = new IncomeTaxSquare("Income Tax", 39);
		// squares.add(incomeTax1);
		TitleDeed boardwalkTD = new TitleDeed(50, 200, 600, 1400, 1700, 2000, 3000, 200, 200, 200, 200);
		PropertySquare boardwalk = new PropertySquare("Boardwalk", 40, 400, null);
		middleTrack.addSquare(boardwalk);
		
		BonusSquare bonus = new BonusSquare("Bonus", 1);
		innerTrack.addSquare(bonus);
		TitleDeed boylstonStTD = new TitleDeed(30, 160, 470, 1050, 1250, 1500, 2500, 165, 200, 200, 200);
		PropertySquare boylstonSt = new PropertySquare("Boylston Street", 2, 330, null);
		innerTrack.addSquare(boylstonSt);
		TitleDeed newburyStTD = new TitleDeed(40, 185, 550, 1200, 1500, 1700, 2700, 190, 200, 200, 200);
		PropertySquare newburySt = new PropertySquare("Newbury Street", 3, 380, null);
		innerTrack.addSquare(newburySt);
		// Pennsylvania Railroad/TransitStation
		TitleDeed fifthAveTD = new TitleDeed(60, 220, 650, 1500, 1800, 2100, 3600, 215, 300, 300, 300);
		PropertySquare fifthAve = new PropertySquare("Fifth Avenue", 5, 430, null);
		innerTrack.addSquare(fifthAve);
		TitleDeed madisonAveTD = new TitleDeed(60, 220, 650, 1500, 1800, 2100, 3600, 215, 300, 300, 300);
		PropertySquare madisonAve = new PropertySquare("Madison Avenue", 6, 430, null);
		innerTrack.addSquare(madisonAve);
		RollThreeDiceSquare rollThree = new RollThreeDiceSquare("Roll Three", 7);
		innerTrack.addSquare(rollThree);
		TitleDeed wallStTD = new TitleDeed(80, 300, 800, 1800, 2200, 2700, 4200, 250, 300, 300, 300);
		PropertySquare wallSt = new PropertySquare("Wall Street", 8, 500, null);
		innerTrack.addSquare(wallSt);
		TaxrefundSquare taxRefund = new TaxrefundSquare("Tax Refund", 9);
		innerTrack.addSquare(taxRefund);
		//				UtilitySquare gasCompany = new UtilitySquare ("Gas Company", 10, 150, null);
		//				innerTrack.addSquare(gasCompany);
		ChanceSquare chanceTrackInnerTrack = new ChanceSquare("Chance", 11);
		innerTrack.addSquare(chanceTrackInnerTrack);
		TitleDeed floridaAveTD = new TitleDeed(9, 45, 120, 350, 500, 700, 1200, 65, 50, 50, 50);
		PropertySquare floridaAve = new PropertySquare("Florida Avenue", 12, 130, null);
		innerTrack.addSquare(floridaAve);
		HollandTunnelSquare hollandTunnel2 = new HollandTunnelSquare("Holland Tunnel", 13);
		innerTrack.addSquare(hollandTunnel2);
		TitleDeed miamiAveTD = new TitleDeed(9, 45, 120, 350, 500, 700, 1200, 65, 50, 50, 50);
		PropertySquare miamiAve = new PropertySquare("Miami Avenue", 14, 130, null);
		innerTrack.addSquare(miamiAve);
		TitleDeed biscayneAveTD = new TitleDeed(11, 55, 160, 475, 650, 800, 1300, 75, 50, 50, 50);
		PropertySquare biscayneAve = new PropertySquare("Biscayne Avenue", 15, 150, null);
		innerTrack.addSquare(biscayneAve);
		// Short Line Railroad/TransitStation
		// Reverse Direction Square
		TitleDeed lombardStTD = new TitleDeed(17, 85, 240, 475, 670, 1025, 1525, 105, 100, 100, 100);
		PropertySquare lombardSt = new PropertySquare("Lombard Street", 18, 210, null);
		innerTrack.addSquare(lombardSt);
		FreeParkingSquare freeParkingInnerTrack = new FreeParkingSquare("Free Parking", 19);
		innerTrack.addSquare(freeParkingInnerTrack);
		TitleDeed theEmbarcaderoTD = new TitleDeed(17, 85, 240, 475, 670, 1025, 1525, 105, 100, 100, 100);
		PropertySquare theEmbarcadero = new PropertySquare("The Embarcadero", 20, 210, null);
		innerTrack.addSquare(theEmbarcadero);
		TitleDeed fishermansWharfTD = new TitleDeed(21, 105, 320, 780, 950, 1125, 1625, 125, 100, 100, 100);
		PropertySquare fishermansWharf = new PropertySquare("Fisherman's Wharf", 21, 250, null);
		innerTrack.addSquare(fishermansWharf);
//		UtilitySquare telephoneCompany = new UtilitySquare ("Telephone Company", 22, 150, null);
//		innerTrack.addSquare(telephoneCompany);
		CommunityChestSquare communityChestInnerTrack = new CommunityChestSquare("Community Chest", 23);
		innerTrack.addSquare(communityChestInnerTrack);
		TitleDeed beaconStTD = new TitleDeed(30, 160, 470, 1050, 1250, 1500, 2500, 165, 200, 200, 200);
		PropertySquare beaconSt = new PropertySquare("Beacon Street", 24, 330, null);
		innerTrack.addSquare(beaconSt);
		
	}

	public Square getSquare(int index, TrackType type) {
		return getTrack(type).getSquare(index);
	}

	public int getNoOfSquaresOnTrack(TrackType type) {
		return getTrack(type).getSquareNumber();
	}

	public int[] getTransitStationLocationsOnTrack(TrackType type) {
		Track track = getTrack(type);
		return track.getTransitlocations();
	}

	private Track getTrack(TrackType type) {
		if (type == outerTrack.getTrackType())
			return outerTrack;
		else if (type == middleTrack.getTrackType())
			return middleTrack;
		else
			return innerTrack;
	}

}
