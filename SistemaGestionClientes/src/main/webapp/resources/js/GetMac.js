/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getMac() {

    var attributes = {id: 'macApplet',
        code: 'GetMacApplet', width: 1, height: 1};
    var parameters = {jnlp_href: 'GetMacApplet.jnlp'};
    deployJava.runApplet(attributes, parameters, '1.6');

    var mac = macApplet.getMacAddr();
    alert(mac);
    
}