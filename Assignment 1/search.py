# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]


def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print("Start:", problem.getStartState())
    print("Is the start a goal?", problem.isGoalState(problem.getStartState()))
    print("Start's successors:", problem.getSuccessors(problem.getStartState()))
    """
    "*** YOUR CODE HERE ***"

    util.raiseNotDefined()


def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"
    # node = (state, action, cost, path)
    # state in this case is the starting, eg, x and y coordinates
    # action is empty at initial because it just started. eg. north south east west
    # cost is zero at initial because it cost zero
    # path is empty because there is no goal state yet

    initial_node = (problem.getStartState(), '', 0, [])
    frontier = util.Queue()
    frontier.push(initial_node)

    explored = set()
    while True:
        # if frontier = ()
        node = frontier.pop()
        state, action, cost, path = node
        # print("state:", state)
        # print("action:", action)
        # print("cost:", cost)
        # print("path:",  path)
        # if state is not explored, add it to explored.
        if state not in explored:
            explored.add(state)
            if problem.isGoalState(state):
                path += [(state, action)]
                break
            child_nodes = problem.getSuccessors(state)
            for child_node in child_nodes:
                child_state, child_action, child_cost = child_node
                next_node = (child_state, child_action, child_cost + cost, path + [(state, action)])
                frontier.push(next_node)

    actions = []
    for action in path:
        actions.append(action[1])
    actions = actions[1:]
    return actions


def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()


def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def aStarSearch(problem, heuristic=nullHeuristic):
    """
    Search the node that has the lowest combined cost and heuristic first.
    You DO NOT need to implement any heuristic, but you DO have to call it.
    The heuristic function is "manhattanHeuristic" from searchAgent.py.
    It will be pass to this function as second argument (heuristic).
    """
    "*** YOUR CODE HERE FOR TASK 2 ***"

    frontier = util.PriorityQueue()
    initial_node = (problem.getStartState(), [], 0)
    state, action, cost = initial_node
    lowest_cost = {}
    frontier.push((state, action, cost), heuristic(problem.getStartState(), problem))

    while not frontier.isEmpty():
        node = frontier.pop()
        state, action, cost = node

        #check if the new cost is lower than the lowest cost
        if cost < lowest_cost.get(state, -1) or lowest_cost.get(state, -1) < 0:
            lowest_cost[state] = cost
            #exit if goal state
            if problem.isGoalState(state):
                return action
            #expand node successors
            child_nodes = problem.getSuccessors(state)
            for child_node in child_nodes:
                child_state, child_action, child_cost = child_node
                child_h = heuristic(child_state, problem)
                if child_h < 2**9999:
                    next_node = (child_state, action + [child_action], cost + child_cost)
                    frontier.push(next_node, child_cost + cost + heuristic(child_state, problem))
    return []



# Extensions Assignment 1
def enforcedHillClimbing(problem, heuristic=nullHeuristic):
    """
    Local search with heuristic function.
    You DO NOT need to implement any heuristic, but you DO have to call it.
    The heuristic function is "manhattanHeuristic" from searchAgent.py.
    It will be pass to this function as second argument (heuristic).
    """
    "*** YOUR CODE HERE FOR TASK 1 ***"

    def improve(initial_node):
        frontier = util.Queue()
        frontier.push(initial_node)
        initial_state, initial_action, initial_cost, initial_path = initial_node
        explored = set()
        while not frontier.isEmpty():
            node = frontier.pop()
            state, action, cost, path = node
            if state not in explored:
                explored.add(state)
                if heuristic(state, problem) < heuristic(initial_state, problem):
                    return node

                child_nodes = problem.getSuccessors(state)
                for child_node in child_nodes:
                    child_state, child_action, child_cost = child_node
                    next_node = (child_state, child_action, child_cost + cost + heuristic(child_state, problem),
                                 path + [(state, action)])
                    frontier.push(next_node)

    initial_node = (problem.getStartState(), '', 0, [])
    state, action, cost, path = initial_node
    while not problem.isGoalState(state):
        initial_node = improve(initial_node)
        state, action, cost, path = initial_node
    path += [(state, action)]
    actions = []
    for action in path:
        actions.append(action[1])
    actions = actions[1:]
    return actions


def jumpPointSearch(problem, heuristic=nullHeuristic):
    """
    Search by pruning non-critical neighbors with jump points.
    You DO NOT need to implement any heuristic, but you DO have to call it.
    The heuristic function is "manhattanHeuristic" from searchAgent.py.
    It will be pass to this function as second argument (heuristic).
    """
    "*** YOUR CODE HERE FOR TASK 3 ***"
    frontier = util.PriorityQueue()
    initial_node = (problem.getStartState(), [], 0)
    state, action, cost = initial_node
    lowest_cost = {}
    frontier.push((state, action, cost), heuristic(problem.getStartState(), problem))
    frontier.push((state, action, cost, (-1,-1)), cost + heuristic(state, problem))

    while not frontier.isEmpty():
        node = frontier.pop()
        state, action, cost = node

        # check if the new cost is lower than the lowest cost
        if cost < lowest_cost.get(state, -1) or lowest_cost.get(state, -1) < 0:
            lowest_cost[state] = cost
            # exit if goal state
            if problem.isGoalState(state):
                return action
            # expand node successors
            child_nodes = problem.getSuccessors(state)
            for child_node in child_nodes:
                child_state, child_action, child_cost = child_node
                child_h = heuristic(child_state, problem)
                if child_h < 2 ** 9999:
                    next_node = (child_state, action + [child_action], cost + child_cost)
                    frontier.push(next_node, child_cost + cost + heuristic(child_state, problem))
    return []


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
ehc = enforcedHillClimbing
jps = jumpPointSearch
