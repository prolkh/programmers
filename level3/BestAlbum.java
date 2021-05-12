// https://programmers.co.kr/learn/courses/30/lessons/42579?language=java
// 해시
// 베스트앨범


// 스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.

// 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
// 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
// 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
// 노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.

// 제한사항
// genres[i]는 고유번호가 i인 노래의 장르입니다.
// plays[i]는 고유번호가 i인 노래가 재생된 횟�입니다.
// genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
// 장르 종류는 100개 미만입니다.
// 장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
// 모든 장르는 재생된 횟수가 다릅니다.
// 입출력 예
// genres	plays	return
// ["classic", "pop", "classic", "classic", "pop"]	[500, 600, 150, 800, 2500]	[4, 1, 3, 0]
// 입출력 예 설명
// classic 장르는 1,450회 재생되었으며, classic 노래는 다음과 같습니다.

// 고유 번호 3: 800회 재생
// 고유 번호 0: 500회 재생
// 고유 번호 2: 150회 재생
// pop 장르는 3,100회 재생되었으며, pop 노래는 다음과 같습니다.

// 고유 번호 4: 2,500회 재생
// 고유 번호 1: 600회 재생
// 따라서 pop 장르의 [4, 1]번 노래를 먼저, classic 장르의 [3, 0]번 노래를 그다음에 수록합니다.

// ※ 공지 - 2019년 2월 28일 테스트케이스가 추가되었습니다.


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

class Solution {
	public int[] solution(String[] genres, int[] plays) {
		//저장하기 쉽게 song 객체를 만든다.
		class Song {
			public String genre;
			public int play;
		}
		
		Map<String, Integer> genreSum = new HashMap<>();
		Map<Integer, Song> playCount = new HashMap<>();
		
		//장르 들은 횟수 총 합산 및 playCount 맵 생성
		for(int i=0; i<genres.length; i++) {
			genreSum.put(genres[i],  genreSum.getOrDefault(genres[i], 0)+plays[i]);
			Song s = new Song();
			s.genre = genres[i];
			s.play  = plays[i];
			playCount.put(i, s);
		}
		
		//장르별로 순위를 결정한다.
		List<Map.Entry<String, Integer>> list = new LinkedList<>(genreSum.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return (o1.getValue()-o2.getValue())* -1;
			}
		});
		
		//LinkedHashMap에 저장
		int rank=0;
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
		for(Map.Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), rank++);
		}
		
		//가장 많이 들은 장르를 선정한 뒤에 거기서 두 곡을 뽑아봅니다.
		List<Map.Entry<Integer, Song>> list2 = new LinkedList<>(playCount.entrySet());
		
		Collections.sort(list2, new Comparator<Map.Entry<Integer, Song>>(){
			@Override
			public int compare(Entry<Integer, Song> o1, Entry<Integer, Song> o2) {
				Song s1 = o1.getValue();
				Song s2 = o2.getValue();
				
				int comparision = sortedMap.get(s1.genre).compareTo(sortedMap.get(s2.genre));
				return comparision==0 ? (s1.play - s2.play)*-1 : comparision;
			}
		});
		
		// 장르. 플레이번호 순으로 정렬
		Map<Integer, String> sortedSong = new LinkedHashMap<>();
		for(Map.Entry<Integer, Song> entry : list2) {
			sortedSong.put(entry.getKey(), entry.getValue().genre);
		}
		
		List<Integer> answer = new LinkedList<>();
		Stack<String> genreStk = new Stack<>();
		
		for(Integer key : sortedSong.keySet()) {
			String value = sortedSong.get(key);
			if(genreStk.isEmpty() || genreStk.peek().equals(value)) {
				if(genreStk.size()<2) {
					genreStk.push(value);
					answer.add(key);
				}
			} else {
				// stack이 비어있지 않고 genreStk의 peek()이 value와 일치하지 않는다면
				// stack을 비우고  다시 넣는다.
				genreStk.clear();
				genreStk.push(value);
				answer.add(key);
			}
		}
		
		return answer.stream().mapToInt(i->i).toArray();
	}
}

