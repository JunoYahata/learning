/**
 * 
 */

const button = document.querySelector('#list');

button.addEventListener('click',() => {
    document.querySelector('#listView').classList.toggle('hidden');
});
