/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function toggle(source) {
  checkboxes = document.getElementsByName('foo');
  for(var i=0, n=checkboxes.length;i<n;i++) {
    checkboxes[i].checked = source.checked;
  }
}