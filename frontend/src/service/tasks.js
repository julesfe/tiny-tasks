import {getRequest} from './common';

const baseURL = '/tasks';

// TODO: TaskRequest Model
export const createTask = date => {
  return fetch(`${baseURL}`, postRequest({TaskRequest: taskrequest})).then(
    response => {
      if (!response.ok) {
        throw new Error(
          `Es konnte kein Task angelegt werden (Status: ${response.status})`)
      }
      return response.json()
    })
};

export const getTasks = () => {
  return fetch(`${baseURL}`, getRequest()).then(response => {
    if (!response.ok) {
      throw new Error(
        `Es konnten keine Tasks geladen werden (Status: ${response.status})`);
    }
    return response.json();
  })
};

export const getTasksByEmail = email => {
  const queryString = qs.stringify(email, {addQueryPrefix: true});
  return fetch(`${baseURL}/${email}`, getRequest()).then(response => {
    if (!response.ok) {
      throw new Error(
        `Es konnten keine Tasks geladen werden (Status: ${response.status})`);
    }
    return response.json();
  })
};

export const deleteTask = id => {
  return fetch(`${baseURL}/${id}`, deleteRequest()).then(response => {
    if (response.status === 409) {
      return response.json().then(body => {
        throw new Error(body.reason)
      })
    }
    if (!response.ok) {
      throw new Error(
        `Es konnte kein Task gelöscht werden (Status: ${response.status})`)
    }
    return true
  })
};

