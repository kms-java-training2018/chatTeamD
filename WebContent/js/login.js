
document.addEventListener('DOMContentLoaded', function() {
   // ▼class名が「placeholder」の要素を対象にしてプレースホルダ機能を加える
   var targets = document.getElementsByClassName('placeholder');
   for (var i=0 ; i<targets.length ; i++) {
      // ▼1.フォーカスを得た際の処理：
      targets[i].onfocus = function () {
         // プレースホルダが表示されていれば
         if( this.value == this.title ) {
            this.value='';                // プレースホルダの文字列を消す
            this.style.color = '#000000'; // 入力欄の文字色を黒色に戻す
         }
      }
      // ▼2.フォーカスを失った際の処理：
      targets[i].onblur = function () {
         // 入力内容が空(またはプレースホルダと同じ文字列)なら
         if( this.value == '' || this.value == this.title ) {
            this.value = this.title;      // プレースホルダを表示
            this.style.color = '#808080'; // 文字色を灰色にする
         }
      }
      // ▼3.デフォルトの表示：
      targets[i].onblur();
   }
});