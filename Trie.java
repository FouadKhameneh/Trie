import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

public class Trie {

	public class TrieNode {
		Map<Character, TrieNode> children;
		char c;
		boolean isWord;

		public TrieNode(char c) {
			this.c = c;
			children = new HashMap<>();
		}

		public TrieNode() {
			children = new HashMap<>();
		}

		public void insert(String word) {
			if (word == null || word.isEmpty())
				return;
			char firstChar = word.charAt(0);
			TrieNode child = children.get(firstChar);
			if (child == null) {
				child = new TrieNode(firstChar);
				children.put(firstChar, child);
			}

			if (word.length() > 1)
				child.insert(word.substring(1));
			else
				child.isWord = true;
		}

	}

	TrieNode root;

	public Trie(List<String> words) {
		root = new TrieNode();
		for (String word : words)
			root.insert(word);

	}

	public boolean find(String prefix, boolean exact) {
		TrieNode lastNode = root;
		for (char c : prefix.toCharArray()) {
			lastNode = lastNode.children.get(c);
			if (lastNode == null)
				return false;
		}
		return !exact || lastNode.isWord;
	}

	public boolean find(String prefix) {
		return find(prefix, false);
	}

	public void suggestHelper(TrieNode root, List<String> list, StringBuffer curr) {
		if (root.isWord) {
			list.add(curr.toString());
		}

		if (root.children == null || root.children.isEmpty())
			return;

		for (TrieNode child : root.children.values()) {
			suggestHelper(child, list, curr.append(child.c));
			curr.setLength(curr.length() - 1);
		}
	}

	public List<String> suggest(String prefix) {
		List<String> list = new ArrayList<>();
		TrieNode lastNode = root;
		StringBuffer curr = new StringBuffer();
		for (char c : prefix.toCharArray()) {
			lastNode = lastNode.children.get(c);
			if (lastNode == null)
				return list;
			curr.append(c);
		}
		suggestHelper(lastNode, list, curr);
		return list;
	}


	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of words
        int numberOfWords = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        // Input the words
        List<String> words = new ArrayList<>();
        for (int i = 0; i < numberOfWords; i++) {
            String word = scanner.nextLine();
            words.add(word);
        }

        Trie trie = new Trie(words);

        // Input the number of prefixes
        int numberOfPrefixes = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        // Input the prefixes
        List<String> prefixes = new ArrayList<>();
        for (int i = 0; i < numberOfPrefixes; i++) {
            String prefix = scanner.nextLine();
            prefixes.add(prefix);
        }

        scanner.close();

           for (String prefix : prefixes) {
            List<String> suggestions = trie.suggest(prefix);

			Collections.sort(suggestions);

            System.out.println(suggestions.size() + " suggestions for '" + prefix + "':");
            if (!suggestions.isEmpty()) {
                System.out.println(String.join(" | ", suggestions));
            }
            System.out.println();
		}
    }

}