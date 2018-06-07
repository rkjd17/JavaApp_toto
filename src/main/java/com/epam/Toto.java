package com.epam;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Scanner;


import com.opencsv.CSVReader;

public class Toto {
	
	

	public static void main(String[] args) throws IOException {
		List<Round> Rounds = ReadRounds();
		//Rounds.stream().forEach(round-> System.out.println(round.getOutcomes()));
		
		
		OptionalLong maxPriceOfAllRoundsIReallyHopeGod = 
				Rounds.stream().mapToLong(round -> round.getHits().stream().mapToLong(hit -> hit.getPrice()).findAny().orElse(0)).max();
		
		System.out.println(maxPriceOfAllRoundsIReallyHopeGod);
		
		Rounds.stream().forEach(round -> {
			System.out.printf("team #1 won: %.2f%%, ",round.getOutcomes().stream().filter(outcome -> outcome==Outcome.ONE).count()/14.0*100);
			System.out.printf("team #2 won: %.2f%%, ",round.getOutcomes().stream().filter(outcome -> outcome==Outcome.TWO).count()/14.0*100);
			System.out.printf("Draw: %.2f%%\n",round.getOutcomes().stream().filter(outcome -> outcome==Outcome.DRAW).count()/14.0*100);
			});
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Date: ");
		LocalDate date = dateParse(sc.nextLine());
		System.out.print("Enter outcomes(14 tip) (1/2/X):");
		List<Outcome> outcomes = explode(sc.nextLine());
//		System.out.println(date);
//		System.out.println(outcomes);
		
		Optional<Round> matchRound = Rounds.stream()
				.filter(round -> round.getYear() == date.getYear())
				.filter(round -> round.getDate().getMonthValue() == date.getMonthValue())
				.filter(round -> round.getDate().getDayOfMonth() == date.getDayOfMonth())
				.map(round->round).findAny();
		//System.out.println(matchRound.toString());
		List<Outcome> matchRoundOutcomes = matchRound.get().getOutcomes();
		int count =0;
		for (int i = 0; i < outcomes.size(); i++) {
			if (outcomes.get(i) == matchRoundOutcomes.get(i)) {
				count++;
			}
		}
		if (count <10) {
			System.out.printf("only %d hits. not enought for any prizes\n",count);
		}else {
			List<Hit> temp = matchRound.get().getHits();
			Collections.reverse(temp);
			System.out.printf("You have %d hits. The reward is: %,dFt",count,temp.get(count-10).getPrice());
		}
		
//		OptionalLong idkWhatsIsThis = matchRoundOutcomes.stream()
//		.mapToLong(outcomeOne -> outcomes
//			.stream()
//			.filter(outcomeTwo->
//				outcomeTwo == outcomeOne
//			).count()
//			).findAny();
//		System.out.println(idkWhatsIsThis);
		
		
		
//		listOne.stream()
//	    .forEach(one -> {listTwo.stream()
//	        .filter(two -> {return two.getId().equals(one.getId());})
//	        .limit(1)
//	        .forEach(two -> {one.setScore(two.getMarks());});
//	    });
		
		
		sc.close();
		
	}
	
	
	
	
	private static List<Round> ReadRounds() throws IOException, FileNotFoundException {
        List<Round> rounds = new ArrayList<Round>();
        try (CSVReader reader = new CSVReader(new FileReader("toto.csv"),';')) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Round round = createRound(nextLine);
                rounds.add(round);
            }
        }
        return rounds;
    }
	
	private static LocalDate dateParse(String date) {
		try {
			return LocalDate.parse(date.replaceAll("[^0-9]+", " ").trim().replace(" ", "-"));
		} catch (DateTimeParseException e) {
			//System.out.println("Nem megfelelõ dátum!");
			return LocalDate.of(1, 1, 1);
		}
		
	}
	
	private static List<Outcome> explode(String s) {	
		List<Outcome> outcomes = new ArrayList<Outcome>();
		if (s.length()!=14) {
			//System.out.println("Nem megfelelõ outcomes!");
			return outcomes;
		}
	    for(int i = 0; i < s.length(); i++)
	    {
	    	 switch(s.toUpperCase().charAt(i)){
             case '1': outcomes.add(Outcome.ONE); break;
             case '2': outcomes.add(Outcome.TWO); break;
             case 'X': outcomes.add(Outcome.DRAW); break;
             default: break;
         }
	    }
	    return outcomes;
	}
	
	private static Round createRound(String[] nextLine) {
		Round round = new Round();
		round.setYear(Integer.valueOf(nextLine[0]));
		round.setWeek(Integer.valueOf(nextLine[1]));
		try {
			round.setRound(Integer.valueOf(nextLine[2]));
		} catch (NumberFormatException e) {
			round.setRound(0);
		}
		try {
			round.setDate((dateParse(nextLine[3])));
		} catch (DateTimeParseException e) {
			round.setDate(LocalDate.of(0001, 01, 01));
		}
		List<Hit> hits = new ArrayList<Hit>();	
		for (int i = 4; i < 14; i=i+2) {
			hits.add(new Hit(Integer.parseInt(nextLine[i]), Long.parseLong(((nextLine[i+1].substring(0, nextLine[i+1].length()-3)).replace(" ", "")))));
		}
		round.setHits(hits);
		List<Outcome> outcomes = new ArrayList<Outcome>() ;
		for (int i = 14; i < 28; i++) {
			String actual = nextLine[i].replace("+", "");
            switch(actual.toUpperCase()){
                case "1": outcomes.add(Outcome.ONE); break;
                case "2": outcomes.add(Outcome.TWO); break;
                case "X": outcomes.add(Outcome.DRAW); break;
                default: break;
            }
		}
		round.setOutcomes(outcomes);
		
		
		return round;
	}

}
