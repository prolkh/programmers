class Solution {
    fun solution(numbers: IntArray, hand: String): String {
        var answer = ""

        var keypad : Map<Int, Array<Int>> = mapOf(
            1 to arrayOf(1, 1)
            , 2 to arrayOf(1, 2)
            , 3 to arrayOf(1, 3)
            , 4 to arrayOf(2, 1)
            , 5 to arrayOf(2, 2)
            , 6 to arrayOf(2, 3)
            , 7 to arrayOf(3, 1)
            , 8 to arrayOf(3, 2)
            , 9 to arrayOf(3, 3)
            , 0 to arrayOf(4, 2)
        )

        var left = arrayOf(4, 1)
        var righ = arrayOf(4, 3)

        for(i in numbers){
            var next = keypad.get(i)?:arrayOf(0,0)

            if(next[1] == 1) {
                left = next
                answer += "L";
                continue;
            } else if(next[1] == 3) {
                righ = next
                answer += "R"
                continue;
            }

            var lx = if(left[0] > next[0]) left[0]-next[0] else next[0]-left[0]
            var ly = if(left[1] > next[1]) left[1]-next[1] else next[1]-left[1]

            var rx = if(righ[0] > next[0]) righ[0]-next[0] else next[0]-righ[0]
            var ry = if(righ[1] > next[1]) righ[1]-next[1] else next[1]-righ[1]

            var ldist = lx + ly
            var rdist = rx + ry

            if(ldist >= rdist) {
                righ = next
                answer += "R"
            } else {
                left = next
                answer += "L"
            }
        }

        return answer
    }
}