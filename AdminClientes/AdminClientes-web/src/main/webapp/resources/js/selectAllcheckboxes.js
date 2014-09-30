/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function toggle(source) {
    var divWithCheckbox = $('#' + source);
    var lielements = divWithCheckbox.children();
    var litodo = $(lielements[0]);
    var inputchechboxtodo = $(litodo.children()[0]);

    if (inputchechboxtodo.is(':checked')) {
        for (var i = 1; i < lielements.size(); i++) {
            var lielement = $(lielements[i]);
            var inputchechbox = $(lielement.children()[0]);
            inputchechbox.prop('checked', true);

        }
    } else {
        for (var i = 1; i < lielements.size(); i++) {
            var lielement = $(lielements[i]);
            var inputchechbox = $(lielement.children()[0]);
            inputchechbox.prop('checked', false);

        }
        
    }
}
function deselecttodo(source) {
    var divWithCheckbox = $('#' + source);
    var lielements = divWithCheckbox.children();
    var litodo = $(lielements[0]);
    var inputchechboxtodo = $(litodo.children()[0]);
    inputchechboxtodo.prop('checked', false);
    
}
