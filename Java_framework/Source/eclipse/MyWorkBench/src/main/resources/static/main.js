/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);

/**
 * タブ切り替えの処理を追加
 */

document.querySelectorAll('.tab_radio').forEach((tab) => {
	tab.addEventListener('click',(e) => {
		document.querySelectorAll('.contents').forEach((view) => {
			view.classList.add('hidden');
		});
    	document.querySelector(e.target.dataset.contents).classList.remove('hidden');
    });
});

/**
 * パラメータからタブ指定をチェックし、対応するタブを表示状態とする
 */

const tab = url.searchParams.get("tab");

if (Boolean(tab)){
    document.querySelector('#main_contents').classList.add('hidden');
    document.querySelector('#'+tab+'_contents').classList.remove('hidden');
    document.querySelector('#'+tab+'_radio').checked = true;
}
