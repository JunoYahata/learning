/**
 * 
 */


document.querySelectorAll('.menu').forEach((tab) => {
	tab.addEventListener('click',(e) => {
		document.querySelectorAll('.main').forEach((view) => {
			view.classList.add('hidden');
		});
    	document.querySelector(e.target.dataset.param).classList.remove('hidden');
    });
});
