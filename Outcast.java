/* *****************************************************************************
 *  Name:K
 *  Date:
 *  Description:
 **************************************************************************** */

public class Outcast {
    private WordNet wn;

    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new IllegalArgumentException();
        this.wn = wordnet;
    }         // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException();
        int dmax = 0;
        String dn = null;
        for (String n : nouns) {
            int temp = 0;
            for (String ni : nouns) {
                temp += wn.distance(n, ni);
            }
            if (temp > dmax) {
                dmax = temp;
                dn = n;
            }
        }
        return dn;
    }   // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {

    }  // see test client below
}
