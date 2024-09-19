




const SUCCESS_MESSAGE = "";

/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);

/**
 * タブ切り替えの処理を追加
 */

document.querySelectorAll('.tab_radio').forEach((tab) => {
	tab.addEventListener('click', (e) => {
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

if (Boolean(tab)) {
	document.querySelector('#main_contents').classList.add('hidden');
	document.querySelector('#' + tab + '_contents').classList.remove('hidden');
	document.querySelector('#' + tab + '_radio').checked = true;
}

/**
 * リンク操作画面のダイアログ作成
 */

document.querySelectorAll('.update_button').forEach((tab) => {
	tab.addEventListener('click', (e) => {
		document.querySelector('.frame').src = "link/update-action/" + e.target.dataset.uid;
		document.querySelector('.dialog').showModal();
	});
});


function delayDialogClose(time) {
	setTimeout("dialogClose()", time);
}

function dialogClose() {
	document.querySelector('.dialog').close();
	location.reload()

}

window.addEventListener('message', e => {
	console.log(e.data);
	if (e.data == "success") {
		document.querySelector('.dialog').removeChild(document.querySelector('.frame'));
		document.querySelector('#dialog_message').classList.remove('hidden');
		delayDialogClose(3000);
	}

	console.log(e.data);

});



