xke-memory-server [![Build Status](https://drone.io/github.com/sophietk/xke-memory-server/status.png)](https://drone.io/github.com/sophietk/xke-memory-server/latest)
=================

A REST server to play memory game

### Game rules

Each turn, choose two cards to flip. If the two cards match, they are marked and should not be replayed. The game is finished when all pairs of cards are found.

- play less or more than two cards -> -1 pt, no card is flipped
- play outside the grid -> -1 pt for each outside card
- flip an already found card -> -3 pts
- find two identical cards -> + 10 pts

### Play

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

### Scores

#### Register player

Request:
```
URL: /scores/register
Method: POST
Content-Type: application/json
Body: myemail@mail.com              // player identifier
```

Response:
```
ok
```

#### Total scores

Request:
```
URL: /scores
Method: GET
Content-Type: application/json
```

Response:
```
{
  "192.168.0.18": 104,              // each player total scores
  "192.168.0.47": 28
}
```

#### Same game scores

Request:
```
URL: /scores/game/1                 // game id
Method: GET
Content-Type: application/json
```

Response:
```
{
  "192.168.0.18": 7,                // each player scores in this game
  "192.168.0.47": 10,
  "player@mail.com": 3
}
```

#### Same player scores

Request:
```
URL: /scores/player/192.168.0.18    // player identifier (ip if not registered)
Method: GET
Content-Type: application/json
```

Response:
```
{
  "1": 7                            // player scores for each round
}
```

---

### Server admin

Package and launch server:
```bash
git clone git@github.com:sophietk/xke-memory-server.git
cd xke-memory-server
mvn clean package
java -jar target/xke-memory-server-1.0-SNAPSHOT-shaded.jar
```

You can start a server with specific admin password and port:
```bash
java -jar target/xke-memory-server-1.0-SNAPSHOT-shaded.jar adminpass 3000
```

You can start and stop several servers with scripts, listening to different ports (edit to change admin passwords):
```bash
./scripts/run-servers.sh 4
./scripts/stop-servers.sh
```

#### Current game

Request:
```
URL: /admin/game
Method: GET
X-Adminpass: adminpass                // set X-Adminpass header with your admin password
Content-Type: application/json
```

#### New game

Request:
```
URL: /admin/new
Method: POST
X-Adminpass: adminpass
Content-Type: application/json
Body: 4                             // game size (4x4)
```

#### Get logs

Request:
```
URL: /admin/logs
Method: GET
X-Adminpass: adminpass
Content-Type: application/json
```

#### Reset server

Request:
```
URL: /admin/reset
Method: POST
X-Adminpass: adminpass
Content-Type: application/json
```

#### Set players turns delay

Request:
```
URL: /admin/tempo
Method: POST
X-Adminpass: adminpass
Content-Type: application/json
Body: 1000                          // turn delay (milliseconds)
```
