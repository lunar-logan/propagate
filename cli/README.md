
# Supported features

### Server related
1. Connect to `Propagate` server
2. Health check of the server 
3. Fetch basic server metrics, like:
    1. Uptime
    2. Current Time
    3. Connections 
    4. CPU/Memory

### Feature flag related
4. List all feature flags
5. Search feature flags on the basic of:
   1. Name
   2. Author
   3. Tags/Labels
6. Describe a single feature flag
7. Create a new feature flag 
8. Archive a feature flag
9. Fetch basic usage metrics related to a single feature flag
   
### Namespace related 
10. List all namespaces 
11. Delete a namespace 

# Examples 

Connection parameters need to be defined in a `config.yml` file.

Login to Propagate:
```
propctl login 
User ==> 
Password ==>  
```

Create or update a feature flag
```
propctl apply ff -f <filename.json>
```

Search or list feature flags (arguments are `AND`ed)
```
propctl get ff name='Something just like thi*' author='Anurag' [--limit=10] [--offset=1] [--output=json] 
```

Describe a feature flag:
```
propctl describe ff key=<key> [--output=json] 
```

Delete a feature flag
```
propctl delete ff key=<key>
```

# Low level design


