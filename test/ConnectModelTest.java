

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectModelTest {
    private ConnectModel model;

    @BeforeEach
    public void starter() {
        model = new ConnectModel();
    }

    @Test
    public void verticalWin() {
        model.playTurn(0);
        assertFalse(model.getCurrentPlayer());
        model.playTurn(1);
        model.playTurn(0);
        model.playTurn(2);
        model.playTurn(0);
        model.playTurn(3);
        model.playTurn(0);
        assertFalse(model.playTurn(5));
        assertEquals(1, model.checkWinner(), "player 1 wins vertically");
    }

    @Test
    public void horizontalWin() {
        model.playTurn(0);
        model.playTurn(1);
        model.playTurn(1);
        model.playTurn(2);
        model.playTurn(0);
        model.playTurn(3);
        model.playTurn(1);
        model.playTurn(4);
        assertEquals(2, model.checkWinner(), "player 2 wins horizontally");
    }

    @Test
    public void frontDiagonalWin() {
        model.playTurn(0);
        model.playTurn(1);
        model.playTurn(1);
        model.playTurn(2);
        model.playTurn(2);
        model.playTurn(3);
        model.playTurn(2);
        model.playTurn(3);
        model.playTurn(3);
        model.playTurn(5);
        model.playTurn(3);
        assertEquals(1, model.checkWinner(), "player 1 wins front diag");
    }

    @Test
    public void backDiagonalWin() {
        model.playTurn(3);
        model.playTurn(2);
        model.playTurn(2);
        model.playTurn(1);
        model.playTurn(1);
        model.playTurn(0);
        model.playTurn(1);
        model.playTurn(0);
        model.playTurn(0);
        model.playTurn(3);
        model.playTurn(0);
        assertEquals(1, model.checkWinner(), "player 1 wins front diag");
    }

    @Test
    public void addToFullColumn() {
        model.playTurn(0);
        model.playTurn(0);
        model.playTurn(0);
        model.playTurn(0);
        model.playTurn(0);
        model.playTurn(0);
        assertFalse(model.playTurn(0), "add to full column");
    }

    @Test
    public void playAfterHGameOver() {
        model.playTurn(0);
        model.playTurn(1);
        model.playTurn(1);
        model.playTurn(2);
        model.playTurn(0);
        model.playTurn(3);
        model.playTurn(1);
        model.playTurn(4);
        assertFalse(model.playTurn(5));
    }

    @Test
    public void playAfterVGameOver() {
        model.playTurn(0);
        model.playTurn(1);
        model.playTurn(0);
        assertEquals(1, model.getCell(0, 4));
        model.playTurn(2);
        model.playTurn(0);
        model.playTurn(3);
        model.playTurn(0);
        assertTrue(!model.playTurn(5));
    }
}
