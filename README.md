xke-memory-server [![Build Status](https://drone.io/github.com/sophietk/xke-memory-server/status.png)](https://drone.io/github.com/sophietk/xke-memory-server/latest)
=================

A REST server to play memory game

### game rules

Each turn, choose two cards to flip. If the two cards match, they are marked and should not be replayed. The game is finished when all pairs of cards are found.

- play less or more than two cards -> -1 pt, no card is flipped
- play outside the grid -> -1 pt for each outside card
- flip an already found card -> -3 pts
- find two identical cards -> + 10 pts

### play

Request:
```
URL: /play
Method: POST
Content-Type: application/json
Body: [ [0, 1], [2, 3] ]            // cards positions in the grid
```

Response:
```
{
  "gameId": 1,                      // game round
  "progress": 5.0,                  // game progress, in percents
  "turn": {
    "turnScore": 0,                 // score in this turn
    "cards": [
      {
        "symbol": "coat",           // first card played
        "color": "blue",
        "found": false
      },
      {
        "symbol": "dog",            // second card played
        "color": "green",
        "found": false
      }
    ],
    "message": null                 // warn message if exists
  },
  "gameScore": 7                    // total score of the game
}
```

### scores

#### same game scores

Request:
```
URL: /scores/game/1
Method: GET
Content-Type: application/json
```

Response:
```
{
  "192.168.0.18": 7,                // each player scores in this game
  "192.168.0.47": 10
}
```

#### same player scores

Request:
```
URL: /scores/player/192.168.0.18
Method: GET
Content-Type: application/json
```

Response:
```
{
  "1": 7                            // player scores for each round
}
```
