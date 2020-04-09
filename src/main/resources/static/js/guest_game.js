/**
 * ゲストゲーム画面のJSファイル
 *
 */
const url = "http://localhost:5000/guest/game/history?callback=?"

$(function() {
  //
  $("#historyButton").click(function() {
    $.getJSON(url,
      {
        gameName: $("#historyButton").attr("data-game")
      }
    )
    .done(function(data) {
      //
      if (data.results) {
        const result = data.results[0];
        console.log(result);
      } else {
        console.log("データ取得失敗")
      }
    });
  });
});
