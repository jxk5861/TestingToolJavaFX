package testing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import graphs.Vertex;
import graphs.paths.GraphPath;

public class DataFlowTesting {
	public static List<GraphPath> duPaths(List<Vertex> declares, List<Vertex> uses){
		List<GraphPath> duPaths = new ArrayList<>();
		
		for(Vertex declare : declares) {
			for(Vertex use : uses) {
				duPaths.addAll(duPath(declare, use));
			}
		}
		return duPaths;
	}

	private static void duPathsHelper(LinkedList<Vertex> list, Vertex out, List<GraphPath> paths) {
		// base case 1: v has no edges (fail)
		// base case 2: v has reached the out and paths length > 1(success)

		if (list.size() > 1 && list.peekLast() == out) {
			paths.add(new GraphPath(list));
			return;
		}

		Vertex current = list.getLast();
		if(list.size() > 1 && current == list.getFirst()) {
			// fail
			return;
		}
		
		
		Set<Vertex> adj = current.getAdjacentCopy();
		for (int i = 0; i < list.size() - 1; i++) {
			Vertex c = list.get(i);
			Vertex next = list.get(i + 1);

			if (c == current) {
				if (current.isBranch()) {
					adj.remove(next);
				}
			}
		}

		if (adj.size() == 0) {
			//paths.add(new C1PPath(list, false));
			return;
		}

		for (Vertex v : adj) {
			LinkedList<Vertex> branch = new LinkedList<>(list);
			branch.add(v);
			duPathsHelper(branch, out, paths);
		}
	}
	
	// Find all paths from declare to use without repeating a node.
	private static List<GraphPath> duPath(Vertex declare, Vertex use){
		List<GraphPath> duPaths = new ArrayList<>();
		
//		// bfs?
//		Queue<GraphPath> bfs = new LinkedList<>();
//		GraphPath first = new GraphPath();
//		first.addVertex(declare);
//		bfs.add(first);
//		while(!bfs.isEmpty()) {
//			GraphPath path = bfs.poll();
//			path.getLast();
//			for(Vertex v : path.getLast().getAdjacent()) {
//				if(v.equals(use) && path.size() != 1) {
//					GraphPath next = new GraphPath(path);
//					next.addVertex(v);
//					duPaths.add(next);
//					System.out.println(next);
//				} else if(!path.contains(v)) {
//					GraphPath next = new GraphPath(path);
//					next.addVertex(v);
//					bfs.add(next);
//				}
//			}
//		}
		
//		List<C1PPath> paths = TestCoverageMetrics.c1p(declare, use);
		
		LinkedList<Vertex> queue = new LinkedList<>();
		queue.add(declare);
		duPathsHelper(queue, use, duPaths);
		
		
		return duPaths;
	}
}
