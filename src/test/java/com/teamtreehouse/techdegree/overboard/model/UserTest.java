package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    Board board;
    User daniel;
    User billy;
    Question q;
    Answer answer;

    @Before
    public void setUp() throws Exception {
        board = new Board("Computer Science");
        daniel = board.createUser("Daniel");
        billy = board.createUser("billy");
        q = daniel.askQuestion("What is the meaning of life?");
    }


    @Test
    public void asksersReputationIncreasesFromUpvote() throws Exception {
        boolean b = billy.upVote(q);

        assertEquals(5, daniel.getReputation());
    }

    @Test
    public void answerersReputationIncreasesFromUpvote() throws Exception {
        answer = billy.answerQuestion(q, "42");
        daniel.upVote(answer);

        assertEquals(10, billy.getReputation());
    }

    @Test
    public void answerersGetReputationBoostFromAcceptance() throws Exception {
        answer = billy.answerQuestion(q, "42");
        daniel.acceptAnswer(answer);

        assertEquals(15, billy.getReputation());
    }

    @Test(expected = VotingException.class)
    public void upVotingNotAllowedByAuthorOnQuestion() throws Exception {
        daniel.upVote(q);
    }

    @Test(expected = VotingException.class)
    public void downVotingNotAllowedByAuthorOnQuestion() throws Exception {
        daniel.upVote(q);
    }

    @Test(expected = VotingException.class)
    public void upVotingNotAllowedByAuthorOnAnswer() throws Exception{
        answer = billy.answerQuestion(q, "42");
        billy.upVote(answer);
    }

    @Test(expected = VotingException.class)
    public void downVotingNotAllowedByAuthorOnAnswer() throws Exception {
        answer = billy.answerQuestion(q, "42");
        billy.downVote(answer);
    }

    @Test(expected = AnswerAcceptanceException.class)
    public void onlyQuestionerCanAcceptAnswer() throws Exception {
        answer = billy.answerQuestion(q, "42");
        billy.acceptAnswer(answer);
    }

    @Test
    public void downVotingDecreasesReputation() throws Exception {
        answer = billy.answerQuestion(q, "42");
        daniel.downVote(answer);

        assertEquals(-1, billy.getReputation());
    }
}