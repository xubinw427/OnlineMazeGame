package ca.MazeOnline.Controllers;


import ca.MazeOnline.API.ApiBoardWrapper;
import ca.MazeOnline.API.ApiGameWrapper;
import ca.MazeOnline.Model.gameMechanics.Model;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Game NUmber Not Found.")
class ResourceNotFoundException extends RuntimeException {
}

@RestController
public class ModelController {
    private List<ApiGameWrapper> ApiGames = new ArrayList<>();
    private List<Model> games = new ArrayList<>();

    @GetMapping("/api/about")
    public String getAbout() {
        return "Xubin Wang";
    }

    @GetMapping("/api/games")
    public List<ApiGameWrapper> getGames() {
        return ApiGames;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewPledge() {
        Model game = new Model();
        games.add(game);
        int index = games.size();

        ApiGameWrapper temp = ApiGameWrapper.makeFromGame(game, index);
        ApiGames.add(temp);
        return temp;
    }
    //@ResponseStatus(value = HttpStatus.NOT_FOUND)
    @GetMapping("/api/games/{id}")
    public ApiGameWrapper getOneGame(@PathVariable("id") int gameNumber) {
        //gameNumber = gameNumber - 1;
        for (ApiGameWrapper game : ApiGames) {
            if (game.gameNumber == gameNumber) {
                return game;
            }
        }
        throw new ResourceNotFoundException();
    }

    @GetMapping("/api/games/{id}/board")
    public ApiBoardWrapper getOnePledge(@PathVariable("id") int gameNumber) {

        for (ApiGameWrapper game : ApiGames) {
            if (game.gameNumber == gameNumber)
            {
                ApiBoardWrapper temp = ApiBoardWrapper.makeFromGame(games.get(gameNumber - 1));
                return temp;
            }
        }
        throw new ResourceNotFoundException();
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("api/games/{id}/moves")
    public void playGame(@PathVariable("id") int gameNumber, @RequestBody String move)
    {
        System.out.println("The move is: " + move);
        System.out.println(" ");
        gameNumber = gameNumber - 1;

        if(gameNumber < 0 || gameNumber >= games.size())
            throw new ResourceNotFoundException();

        String movement = " ";
        boolean catsMove = false;
        switch (move)
        {
            case "MOVE_UP":
                movement = "W";
                break;
            case "MOVE_DOWN":
                movement = "S";
                break;
            case "MOVE_LEFT":
                movement = "A";
                break;
            case "MOVE_RIGHT":
                movement = "D";
                break;
            case "MOVE_CATS":
                movement = "m";
                catsMove = true;
                break;
            default:
                throw new IllegalArgumentException();
        }
        System.out.println("Movement is: " + movement);
        System.out.println(" ");

        if(!games.get(gameNumber).canMoveOrNot(movement)) {
            System.out.println("Can not Move!\n");
            throw new IllegalArgumentException();
        }
//        switch (move)
////        {
////            case "MOVE_UP":
////                games.get(gameNumber).playGame("W");
////                break;
////            case "MOVE_DOWN":
////                games.get(gameNumber).playGame("S");
////                break;
////            case "MOVE_LEFT":
////                games.get(gameNumber).playGame("A");
////                break;
////            case "MOVE_RIGHT":
////                games.get(gameNumber).playGame("D");
////                break;
////            case "MOVE_CATS":
////                games.get(gameNumber).moveCats();
////                break;
////            default:
////                throw new IllegalArgumentException();
////        }}
        if(catsMove)
            games.get(gameNumber).moveCats();
        else
            games.get(gameNumber).playGame(movement);
        ApiGameWrapper temp = ApiGameWrapper.makeFromGame(games.get(gameNumber), gameNumber + 1);
        ApiGames.set(gameNumber, temp);
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("api/games/{id}/cheatstate")
    public void makeCheat(@PathVariable("id") int gameNumber, @RequestBody String cheat)
    {
        gameNumber = gameNumber - 1;
        if(gameNumber < 0 || gameNumber >= games.size())
            throw new ResourceNotFoundException();
        switch (cheat)
        {
            case "1_CHEESE":
                games.get(gameNumber).setGoal(1);
                break;
            case "SHOW_ALL":
                for(int i = 0; i < games.get(gameNumber).getMaze().getHeight(); i++)
                {
                    for(int j = 0; j < games.get(gameNumber).getMaze().getWidth(); j++)
                    {
                        games.get(gameNumber).getMapAt(i,j).makeVisible();
                    }
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
        ApiGameWrapper temp = ApiGameWrapper.makeFromGame(games.get(gameNumber), gameNumber + 1);
        ApiGames.set(gameNumber, temp);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {
        // Nothing to do
    }
}
