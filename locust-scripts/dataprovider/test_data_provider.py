
USER_ID_START = 1
USER_ID_END = 1 + 1000000000
CURRENT_USER_ID = USER_ID_START

def get_user_id():
    global CURRENT_USER_ID
    if CURRENT_USER_ID <= USER_ID_END:
        return_user_id = CURRENT_USER_ID
        CURRENT_USER_ID += 1
        return return_user_id
